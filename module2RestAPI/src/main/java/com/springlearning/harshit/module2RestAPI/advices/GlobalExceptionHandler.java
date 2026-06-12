package com.springlearning.harshit.module2RestAPI.advices;

import com.springlearning.harshit.module2RestAPI.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/*
  @RestControllerAdvice is used to handle exceptions globally across all REST controllers in a Spring Boot application.
  It also allows you to define common response handling logic (like error responses) in one central place
  instead of writing it in every controller.

  You don't need to explicitly annotate the 'GlobalExceptionHandler' class with '@Component' because internally,
  @RestControllerAdvice makes use of @ControllerAdvice annotation which itself internally makes use @Component annotation.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
      @ExceptionHandler is used in Spring to define a method that handles specific exceptions thrown in a controller
      or globally.
      When that exception occurs, Spring automatically calls this method.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                                .status(HttpStatus.NOT_FOUND)
                                .message(exception.getMessage())
                                .build();

        return buildResponseEntityWithError(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message(exception.getMessage())
                                .build();
        return buildResponseEntityWithError(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        /*
          'BindingResult' is an object that stores validation and data binding errors when Spring converts
           request data into a Java object. It is created internally by Spring’s DataBinder.

           BindingResult is not present in all exceptions. It is only used in validation and data binding scenarios
           like MethodArgumentNotValidException or @ModelAttribute validation.
           It does not exist in general exceptions like NullPointerException or RuntimeException.
         */
        List<String> errors = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subErrors(errors)
                .build();

        return buildResponseEntityWithError(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildResponseEntityWithError(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
