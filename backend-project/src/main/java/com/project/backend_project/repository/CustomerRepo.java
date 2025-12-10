package com.project.backend_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend_project.entities.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerRepo extends JpaRepository<Customer, Long>{    

    List<Customer> findByLastName(String lastName); 
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByLastName(String lastName);
    boolean existsByLastName(String lastName);
}
