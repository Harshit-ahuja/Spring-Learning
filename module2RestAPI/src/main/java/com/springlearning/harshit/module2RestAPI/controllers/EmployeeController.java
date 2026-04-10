package com.springlearning.harshit.module2RestAPI.controllers;

import com.springlearning.harshit.module2RestAPI.dto.EmployeeDTO;
import com.springlearning.harshit.module2RestAPI.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(employeeId, employeeDTO);
        if(updatedEmployee == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(updates, employeeId);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }


    /* In Spring Boot, controller methods should be public so Spring can reliably map and invoke them for HTTP requests.
    Methods with default or protected access may work but are not recommended,
    while private methods usually cannot be mapped, causing the endpoint to fail (e.g 404) */
}
