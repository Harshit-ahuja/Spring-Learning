package com.springlearning.harshit.module2RestAPI.services;

import com.springlearning.harshit.module2RestAPI.dto.EmployeeDTO;
import com.springlearning.harshit.module2RestAPI.entities.EmployeeEntity;
import com.springlearning.harshit.module2RestAPI.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

        return modelMapper.map(employeeEntity, EmployeeDTO.class);

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
}
