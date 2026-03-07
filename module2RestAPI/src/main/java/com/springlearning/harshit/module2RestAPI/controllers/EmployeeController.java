package com.springlearning.harshit.module2RestAPI.controllers;

import com.springlearning.harshit.module2RestAPI.dto.EmployeeDTO;
import com.springlearning.harshit.module2RestAPI.entities.EmployeeEntity;
import com.springlearning.harshit.module2RestAPI.repositories.EmployeeRepository;
import com.springlearning.harshit.module2RestAPI.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Not necessary to provide a path here in GetMapping annotation.
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return employeeService.getAllEmployees();
    }

    /* @RequestBody binds the HTTP request body to a Java object,
       allowing Spring to automatically deserialize JSON (or XML) into that object.
    */
    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
        return employeeService.createNewEmployee(inputEmployee);
    }

    @PutMapping
    public String updateEmployeeById() {
        return "Hello from put";
    }
}
