package com.springlearning.harshit.module2RestAPI.services;

import com.springlearning.harshit.module2RestAPI.dto.EmployeeDTO;
import com.springlearning.harshit.module2RestAPI.entities.EmployeeEntity;
import com.springlearning.harshit.module2RestAPI.exceptions.ResourceNotFoundException;
import com.springlearning.harshit.module2RestAPI.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);

        /* As per Spring web project structure, Controllers should only interact with DTOs and not the Entity.
        So, we'll transform EmployeeEntity to EmployeeDTO using ModelMapper dependency(added in pom)
        and return EmployeeDTO to the controller */

        /* One way to use ModelMapper is to create a new Object of ModelMapper as shown below :

            ModelMapper mapper = new ModelMapper();
            return mapper.map(employeeEntity, EmployeeDTO.class);

        But this way, in every other method (like getAllEmployees, createNewEmployee etc), we'll have to create a separate new object of ModelMapper.
        So, Instead of creating new object everytime, we'll configure a bean (that's the 'spring' way of dealing with objects). */

        if(employeeEntity == null) {
            return null;
        } else {
            return modelMapper.map(employeeEntity, EmployeeDTO.class);
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        return employeeEntityList
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity entityToSave = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(entityToSave);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        if(!existsByEmployeeId(employeeId)) {
            throw new ResourceNotFoundException("Employee Not Found with id : " + employeeId);
        }

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeId);

        /*
         The 'save' method of JPA Repository broadly works like:

         1. If the entity's ID is null → it is treated as a new entity and Hibernate performs an INSERT.
         2. If the entity's ID is not null and exists in the database → it is treated as an existing entity and Hibernate performs an UPDATE.
         3. If the entity's ID is not null but does NOT exist in the database → Hibernate attempts a merge but will throw a
            StaleObjectStateException (Hibernate thinks the row should exist but it does not).

         So, to safely update an entity, always retrieve it first using findById(), modify the fields, and then call save() method.
        */
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    private boolean existsByEmployeeId(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        if(!existsByEmployeeId(employeeId)) {
            throw new ResourceNotFoundException("Employee Not Found with id : " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long employeeId) {
        if(!existsByEmployeeId(employeeId)) {
            throw new ResourceNotFoundException("Employee Not Found with id : " + employeeId);
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();

        // We'll use 'java reflection' to update only those fields of 'employee entity' which are passed in the request body by the client.
        updates.forEach((key, value) -> {
            if(key == "salary") key = "salaryPerMonth";
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });

        EmployeeEntity updatedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(updatedEmployeeEntity, EmployeeDTO.class);
    }

    /*
      Java Reflection is a feature in Java that allows a program to inspect, analyze, and modify
      classes, methods, fields, and constructors at runtime (during execution) instead of compile time.
      It is provided by the java.lang.reflect package.
      In simple terms, Reflection lets a Java program look at its own structure and manipulate it dynamically.
      Reflection is used when the program does not know the class or method at compile time.

      Reflection API is the set of classes and interfaces provided by Java to implement reflection.
      Classes like 'Class', 'Method', 'Field', 'Constructor', and 'Modifier' form the Reflection API.

       **** Example of Usage of Java Reflection ****

       Consider a 'User' class as shown below :-
       |------------------------------------------------|
       | public class User{                             |
       |     private String name;                       |
       |                                                |
       |     public void setName(String name) {         |
       |        this.name = name;                       |
       |     }                                          |
       | }                                              |
       |________________________________________________|

       Normally, we can create an object of class User and call the setter method to set the name. Like :
       |--------------------------|
       | User user = new User();  |
       | user.setName("Harshit"); |
       |__________________________|

       Using Reflection API, we can do this like :
       |-----------------------------------------------------------|
       | Class<?> cls = Class.forName("User");                     |
       | Object obj = cls.getDeclaredConstructor().newInstance();  |
       | Method method = cls.getMethod("setName", String.class);   |
       | method.invoke(obj, "Harshit");                            |
       |___________________________________________________________|

       We can even access the private fields directly using the reflection API
       and we can modify the data of private fields even without calling the setter. Like :
       |------------------------------------------------|
       | Field field = cls.getDeclaredField("name");    |
       | field.setAccessible(true);                     |
       |  field.set(obj, "Harshit");                    |
       |________________________________________________|


       **** Advantages and Disadvantages of Java Reflection ****

       Advantages -> Runtime Inspection of classes, Dynamic object creation, Dynamic method invocation
                    and extremely useful for frameworks and libraries.
       Disadvantages -> Performance Overhead (Slow Execution),
                        Security Risks (Private Members Access),
                        No Compile Time Checking
                        & Hard to Maintain and Debug
     */
}
