package com.springlearning.harshit.module2RestAPI.controllers;

import com.springlearning.harshit.module2RestAPI.dto.EmployeeDTO;
import com.springlearning.harshit.module2RestAPI.exceptions.ResourceNotFoundException;
import com.springlearning.harshit.module2RestAPI.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /* If a class has only one constructor, @Autowired is NOT required — Spring injects dependencies automatically.
       If a class has multiple constructors, @Autowired is REQUIRED to tell Spring which constructor to use.
       Setter and field injection ALWAYS require @Autowired — there is no automatic rule for them.
     */
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /* ResponseEntity is a class from Spring Framework used to represent the complete HTTP response returned by a REST API.
    ResponseEntity helps you to control : HTTP Status Code(200,404 etc), Response Body(Data sent to client) & HTTP Headers.
    So instead of just returning data, you return a complete HTTP response object. */
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        if(employeeDTO == null) {
            throw new ResourceNotFoundException("Employee not found with id : " + id);
        }
        else {
            return ResponseEntity.ok(employeeDTO);
        }
    }

    // Not necessary to provide a path here in GetMapping annotation.
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /* @RequestBody binds the HTTP request body to a Java object,
       allowing Spring to automatically deserialize JSON (or XML) into that object.

       @Valid annotation enforces the controller to validate the data of EmployeeDTO object
       according to the validation constraints provided in EmployeeDTO class - like '@NotBlank', 'NotNull', 'Size' etc)
    */
    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Long employeeId, @RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(employeeId, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(updates, employeeId);
        return ResponseEntity.ok(employeeDTO);
    }


    /* We can create ExceptionHandlers like this in a controller.
       And this would mean that any Exception occurred in any method of this controller which is of type 'NoSuchElementException'
       would be intercepted by this ExceptionHandler.
       This ExceptionHandler would then send an Appropriate Error Message in the Response Entity.

       However, creating recurring ExceptionHandlers of similar type in every controller is not recommended.
       Therefore, we use @RestControllerAdvice (as used in GlobalExceptionHandler) in order to create ExceptionHandlers
       that intercept Exceptions globally and hence we don't have to individually create ExceptionHandlers in every
       controller class.

          |-----------------------------------------------------------------------------------------|
          | @ExceptionHandler(NoSuchElementException.class)                                         |
          | public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {  |
          |     return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);            |
          | }                                                                                       |
          |_________________________________________________________________________________________|
     */
}

/* In Spring Boot, controller methods should be public so Spring can reliably map and invoke them for HTTP requests.
    Methods with default or protected access may work but are not recommended,
    while private methods usually cannot be mapped, causing the endpoint to fail (e.g 404) */
