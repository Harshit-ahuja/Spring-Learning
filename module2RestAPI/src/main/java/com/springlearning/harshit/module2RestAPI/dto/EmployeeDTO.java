package com.springlearning.harshit.module2RestAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springlearning.harshit.module2RestAPI.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/* DTO (Data Transfer Object) is used to carry data between layers without exposing entity objects directly.
A DTO is just another POJO as defined below */

public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name of the Employee cannot be blank")
    @Size(min = 3, message = "Name of the Employee should not be less than 3 characters")
    private String name;

    @NotBlank(message = "Email of Employee cannot be blank")
    @Email(message = "Email should be valid email")
    private String email;

    @NotNull(message = "Age of Employee cannot be null")
    @Max(value = 60, message = "Age of Employee cannot be more than 60")
    @Min(value = 18, message = "Age of Employee cannot be less than 18")
    private Integer age;

    @NotBlank(message = "Role of Employee cannot be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of the Employee can either be ADMIN or USER")
    @EmployeeRoleValidation // Created this custom validation annotation that serves the same purpose as the annotation commented above
    private String role;

    @JsonProperty("salary")
    @NotNull(message = "Salary of Employee cannot be null")
    @Positive(message = "Salary of Employee cannot be negative or zero")
    @Digits(integer = 6, fraction = 2, message = "Salary of Employee cannot exceed 6 integer digits and 2 fraction digits")
    private Double salaryPerMonth;

    @NotNull(message = "DateOfJoining field of Employee cannot be null")
    @PastOrPresent(message = "DateOfJoining field of Employee cannot be in the future")
    private LocalDate dateOfJoining;

    /* @JsonProperty is an annotation from Jackson.
    It is used to control how Java fields are mapped to JSON properties during serialization and deserialization.

    Serialization → Java Object ➜ JSON conversion
    Deserialization → JSON ➜ Java Object conversion

    @JsonProperty tells Jackson what name should be used in JSON for a field.

    After adding this annotation, any name can be used for the java field (like -> private Boolean isActiveEmployee)
    and jackson will map 'isActive' field of JSON to 'isActiveEmployee' of the java object.
    */
    @JsonProperty("isActive")
    @AssertTrue(message = "Employee must be active")
    private Boolean isActive;

    // Default Constructor
    public EmployeeDTO() {

    }

    // All args constructor
    public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.dateOfJoining = dateOfJoining;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getSalaryPerMonth() {
        return salaryPerMonth;
    }

    public void setSalaryPerMonth(Double salaryPerMonth) {
        this.salaryPerMonth = salaryPerMonth;
    }
}


/*
  WHY IT IS RECOMMENDED TO USE @JsonProperty ANNOTATION WHILE WORKING WITH BOOLEAN FIELDS WHOSE NAME STARTS WITH 'IS' ?

  Using @JsonProperty("isActive") on a boolean field like private Boolean isActive; is beneficial for
  three main reasons:

  -> Prevents Key Stripping: By default, Jackson (the JSON library) follows Java Bean standards and may
     strip the "is" prefix during serialization. This turns your JSON key into "active" instead of "isActive".
     The annotation forces the key to remain "isActive".
  -> Ensures Accurate Mapping: During deserialization (JSON ➜ Java), Jackson might look for a setter named
     setIsActive(). If your code uses a different naming convention (like setActive()), the value might remain null.
     This annotation explicitly maps the JSON key to the correct field.
  -> Decouples API from Code: It allows you to rename your internal Java variable (e.g., to enabled) without
     changing the external JSON structure. This keeps your API Contract stable even if you refactor your code.

    Key Takeaway: > It guarantees that the JSON key matches your requirement regardless of Java's internal
    getter/setter naming logic.
 */
