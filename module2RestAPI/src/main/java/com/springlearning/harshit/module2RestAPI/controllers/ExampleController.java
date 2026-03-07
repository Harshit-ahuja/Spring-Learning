package com.springlearning.harshit.module2RestAPI.controllers;

import com.springlearning.harshit.module2RestAPI.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/* @RestController annotation extends from @Controller annotation only.
Only difference is @ResponseController has an additional @ResponseBody annotation.

@ResponseBody tells Spring to skip ViewResolver mechanism and use HttpMessageConverters instead
so that the java objects returned by controller method can be converted to required response format.
DispatcherServlet detects @ResponseBody on method or @RestController on class via HandlerAdapter
to decide response handling. */
@RestController
@RequestMapping("/example")
public class ExampleController {

    /* @RequestMapping annotation is used to map requests to controller methods.
    It has various attributes to match by URL, HTTP methods, request parameters, headers & media types.
    @GetMapping is same as @RequestMapping.
    Only difference is @GetMapping inherently specifies the HTTP method as GET. */

    /* Mentioning path attribute is optional here.
    We could simply write @GetMapping("/getSecretMessage") here as well */
    @GetMapping(path = "/getSecretMessage")
    public String getMySecretMessaage() {
        return "Secret Message : A2@7y2e7#235#@151";
    }

    @GetMapping("/employees/{employeeId}")
    /* @PathVariable binds the path segment {employeeId} to the method parameter.
    The variable name in the method argument must match the path segment {employeeId} name and case.
    The data type of the path segment {employeeId} determines the type received in the method (Long, String, etc.) */
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return new EmployeeDTO(employeeId, "Harshit", "abc@gmail.com", 25, LocalDate.of(2024, 4, 2), true);
    }

    @GetMapping("/employees/employeeName/{employeeName}")
    /*
     As mentioned earlier, in case of path variables, variable name in the method argument must match the path segment's name and case.
     But in case we need a different name to be used inside the method, we can use the "name" attribute with @PathVariable annotation as shown.
    */
    public EmployeeDTO getEmployeeByName(@PathVariable(name = "employeeName") String nameComingFromPathVariable) {
        return new EmployeeDTO(Long.valueOf(1), nameComingFromPathVariable, "abc@gmail.com", 25, LocalDate.of(2024, 4, 2), true);
    }

    /* Request Param
       The variable name passed in the method argument should match the name of url request parameter.
       http://localhost:8080/info?name=harshit&age=25 -> Example of request parameter
       (required=false) would facilitate the same controller method to handle get requests made to "/info" path even without any request param attached to it.
    */
    @GetMapping("/info")
    public String getInfo(@RequestParam(required = false, name = "queryParamName") String name,
                          @RequestParam(required = false) Integer age) {
        String msg = "Hi ";
        if(name != null) {
            msg+=name;
        }
        msg+=" ";
        if(age != null) {
            msg+=age;
        }
        return msg;
    }
}
