package com.springlearning.harshit.module2RestAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/* DTO (Data Transfer Object) is used to carry data between layers without exposing entity objects directly.
A DTO is just another POJO as defined below */

public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
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
}
