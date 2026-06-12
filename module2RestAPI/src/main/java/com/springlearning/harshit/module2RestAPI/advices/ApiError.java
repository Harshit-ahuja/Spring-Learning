package com.springlearning.harshit.module2RestAPI.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/*
   @Data is a combo annotation.
       - It includes @Getter, @Setter, @ToString, @@EqualsAndHashCode, @RequiredArgsConstructor (for final fields)

   @Builder implements the builder design pattern (useful for creating objects in a clean and readable way)
 */
@Data
@Builder
public class ApiError {

    private HttpStatus status;
    private String message;
    List<String> subErrors;
}
