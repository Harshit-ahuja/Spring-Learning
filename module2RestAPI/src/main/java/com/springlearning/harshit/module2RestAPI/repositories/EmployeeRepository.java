package com.springlearning.harshit.module2RestAPI.repositories;

import com.springlearning.harshit.module2RestAPI.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository is generic repository. We need to provide two things to it:
// The entity that it needs to handle and the type of the primary key of that entity ('ID' in our case)
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
