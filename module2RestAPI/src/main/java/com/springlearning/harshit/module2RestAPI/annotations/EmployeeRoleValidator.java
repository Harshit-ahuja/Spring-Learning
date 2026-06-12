package com.springlearning.harshit.module2RestAPI.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/*
  The validator class - 'EmployeeRoleValidator' is the "Worker".
  This is where you write the actual Java logic to decide if the data coming from the user is valid or not.
*/

/**
 * ConstraintValidator<Annotation, Type> connects annotation with validation logic.
 *
 * Generic breakdown:
 *
 * 1. EmployeeRoleValidation:
 *    - This is the custom annotation this validator is linked to.
 *    - It tells Spring/Jakarta Validation: "run this validator when you see @EmployeeRoleValidation"
 *
 * 2. String:
 *    - This is the type of value being validated.
 *    - In this case, the annotation is applied on a String field (like role in DTO).
 *
 * How to read it simply:
 * - "This validator will validate String values that are marked with @EmployeeRoleValidation"
 *
 * Why this is important:
 * - It allows the same validation framework to work for different data types
 *   (String, Integer, custom objects, etc.) using generics.
 */
public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation, String> {

    /**
     * Core validation method called automatically by the Bean Validation framework.
     *
     * This method contains the actual business rule for validation.
     *
     * @param inputRole
     * - This is the value received from the client request (e.g., "USER", "ADMIN", "MANAGER").
     * - It is the actual field value being validated.
     *
     * @param context
     * - Provides additional information and control over the validation process.
     * - Can be used to:
     *   - Customize error messages dynamically
     *   - Add custom constraint violations
     *   - Modify default validation behavior (advanced use case)
     *
     * @return
     * - true  → if the inputRole is valid according to your rule
     * - false → if the inputRole is invalid and should fail validation
     *
     */
    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext context) {
        if(inputRole == null) {
            return false;
        }
        List<String> acceptableRoles = List.of("USER", "ADMIN");
        return acceptableRoles.contains(inputRole);
    }
}


/*
  HOW THE ACTUAL TALKING BETWEEN ANNOTATION CLASS AND VALIDATOR CLASS HAPPENS AND HOW THE INPUT VALIDATION WORKS:

  -> When a request comes in, Spring maps it to your DTO object.
  -> Spring triggers Bean Validation (@Valid / @Validated) on the DTO.
  -> It scans the DTO fields and finds @EmployeeRoleValidation on 'role'.
  -> Because of @Constraint(validatedBy = EmployeeRoleValidator.class),
     Spring knows which validator should handle this annotation.
  -> Spring creates/uses EmployeeRoleValidator and calls isValid() method.
  -> The field value (e.g., "MANAGER") is passed into isValid().
  -> If isValid() returns false:
        - Validation fails
        - Spring automatically throws MethodArgumentNotValidException
        - A 400 Bad Request response is returned to the client
        - Default or custom message from annotation is included in response
  -> If isValid() returns true:
        - Request continues normally to Controller/Service layer
*/

