package com.springlearning.harshit.module2RestAPI.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/*
 * This class is used to transform all API responses into a standardized structure
 * before they are sent to the client.
 *
 * @RestControllerAdvice is used here because its actual purpose is to provide
 * global cross-cutting behavior for the Spring MVC controller layer.
 * It allows us to intercept controller-related events such as:
 * - Exceptions (using @ExceptionHandler)
 * - Responses (using ResponseBodyAdvice)
 *
 * Therefore, @RestControllerAdvice is used both in GlobalExceptionHandler
 * and GlobalResponseHandler.
 *
 * To standardize all API responses, this class needs:
 * 1. A way to be applied globally to controller responses
 *    (provided by @RestControllerAdvice)
 * 2. A way to intercept and modify the response body
 *    (provided by implementing ResponseBodyAdvice)
 *
 * ResponseBodyAdvice is a Spring MVC extension point that allows modification
 * of the response body after the controller returns a value but before the
 * response is written to the HTTP response and sent to the client.
 * We do not invoke it manually. Spring automatically detects and executes it
 * for eligible controller responses during the response-writing phase.
 * This is part of Spring MVC response lifecycle and works only for controller request-processing pipeline.
 *
 */
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    /*
    * The "supports" method determines whether this advice should apply or not.
    * Returning true here means that it applies to ALL controller responses.
    */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /*
     * This method is where the response modification happens.
     *
     * If response is already APIResponse, it is returned as-is (preventing double wrapping).
     * Otherwise, it is wrapped inside ApiResponse.
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ApiResponse<?>) {
            return body;
        }

        return new ApiResponse<>(body);
    }


/* ------------------------------------------------------------------
 *   How Response Transformation Works :
 *  -----------------------------------------------------------------
 *
 *   1. The controller returns ResponseEntity Object.
 *   2. Spring calls ResponseBodyAdvice before sending response.
 *   3. This class intercepts the response body.
 *   4. It wraps the response inside ApiResponse<T>
 *   5. Final Response is sent to the client
 *
 *  ------------------------------------------------------------------
 *
 *
 *
 *
 *
 *  -------------------------------------------------------------------------------------------------
 *     Important Insight :
 *  -------------------------------------------------------------------------------------------------
 *
 *  ResponseBodyAdvice only modifies the HTTP response body.
 *  It does NOT modify the HTTP status code or headers.
 *
 *  When a controller returns ResponseEntity<EmployeeDTO>,
 *  Spring extracts the body (EmployeeDTO) and passes it to ResponseBodyAdvice.beforeBodyWrite().
 *
 *  GlobalResponseHandler then wraps only the body:
 *
 *    EmployeeDTO
 *         ↓
 *   ApiResponse<EmployeeDTO>
 *
 *  The ResponseEntity metadata remains unchanged:
 *  - HTTP Status Code remains the same (200, 201, 404, etc.)
 *  - HTTP Headers remain the same
 *
 *
 *  Therefore:
 *  ResponseEntity<EmployeeDTO> effectively becomes ResponseEntity<ApiResponse<EmployeeDTO>>
 *  while preserving the original status code and headers.
 *
 * -----------------------------------------------------------------------------------------------------
 *
 *
 *
 *
 *
 *
 *  ------------------------------------------------------------------------------------------------------
 *     Nuance - Normal Flow vs Exception Flow :
 *  ------------------------------------------------------------------------------------------------------
 *
 *  Success flow (Normal controller execution) :
 *
 *      - Controller returns a value
 *              |
 *      - ResponseBodyAdvice.beforeBodyWrite() is triggered
 *              |
 *      - Spring automatically wraps the response into ApiResponse<T>
 *              |
 *      - Final response is sent to the client
 *
 *
 *  Takeaway - ResponseBodyAdviceWorks here because a normal controller return value exists and
 *  wrapping/transforming that return value is a part of Spring MVC's response writing pipeline.
 *
 *  Hence, just returning ResponseEntity from the controller is enough and transformation to ApiResponse
 *  automatically occurs behind the scene.
 *  This is why, even when we are just sending ResponseEntity.ok(employeeDTO) from the controller,
 *  the API response in Postman is well wrapped in ApiResponse object automatically.
 *
 *
 *
 *  Exception Flow :
 *
 *      - Exception is thrown from Controller/Service
 *              |
 *      - Normal return flow is skipped (no controller response body exists)
 *              |
 *      - @ExceptionHandler method handles the exception
 *              |
 *      - A response entity needs to be created manually and returned
 *
 *
 *  Takeaway - There is no controller return body for ResponseBodyAdvice to intercept.
 *  Exception Handling is a separate pipeline from normal response processing.
 *  Exception Handlers act as the final point of response creation for errors.
 *  Spring does not automatically apply ResponseBodyAdvice to exception-generated responses.
 *
 *  Hence, we explicitly build ApiResponse from ApiError inside @ExceptionHandler.
 *
 * -------------------------------------------------------------------------------------------------------
 */



}
