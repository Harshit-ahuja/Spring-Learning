package com.springlearning.harshit.module2RestAPI.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
  This annotation class - 'EmployeeRoleValidation' is the "contract".
  It defines the name of your rule and tells Spring which "Worker" (Validator) is responsible for enforcing it.
*/

/**
 * Defines how long this annotation should be retained in the application lifecycle.
 *
 * RetentionPolicy.RUNTIME means:
 * - The annotation will be available at runtime
 * - Frameworks like Spring can read it using reflection
 * - This is necessary for validation to work during API execution
 */
@Retention(RetentionPolicy.RUNTIME)

/**
 * Defines where this annotation can be applied.
 *
 * ElementType.FIELD:
 * - Can be used on class variables (e.g., DTO fields)
 *
 * ElementType.PARAMETER:
 * - Can be used on method parameters (e.g., Controller inputs)
 *
 * This ensures the annotation is used only in valid and meaningful places.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})

/**
 * Connects this annotation with its validation logic.
 *
 * @Constraint tells the Bean Validation framework (Jakarta Validation):
 * - Which validator class should handle the validation logic
 * - In this case, EmployeeRoleValidator will be executed when validation runs
 *
 * This is the bridge between annotation and actual business validation code.
 */
@Constraint(validatedBy = {EmployeeRoleValidator.class})

/*
  @interface is used because annotations are special metadata types in Java, not normal interfaces.
  A normal interface cannot store annotation values or be used with @ syntax,
  so Java provides @interface for defining custom annotations.
*/
public @interface EmployeeRoleValidation {

    /**
     * Default error message that will be returned when this validation fails.
     * Clients can override this message while using the annotation.
     */
    String message() default "Role of Employee can either be USER or ADMIN";

    /**
     * Used for grouping validations.
     * Helps us apply this constraint selectively in different scenarios
     * like Create, Update, or any custom validation flow.
     *
     * Example: You may want this validation only during Update operation.
     * By default, it's empty which means it applies to all groups.
     */
    Class<?>[] groups() default { };

    /**
     * Used to attach custom metadata (payload) to a constraint.
     * This is mainly for advanced use cases where clients want to pass
     * extra information about the validation failure.
     *
     * In most real-world applications, this is rarely used but is required
     * by the Bean Validation specification.
     */
    Class<? extends Payload>[] payload() default { };
}

/*
Note - We still need to use the @Valid annotation in the controller even if we are creating our custom validation annotation.
And we still need to use the spring-boot-starter-validation dependency.
*/
