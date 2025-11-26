package com.project.backend_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend_project.entities.Person;
import java.util.Optional;


public interface PersonRepo extends JpaRepository<Person, Long>{    

    Optional<Person> findByLastName(String lastName); 
    void deleteByLastName(String lastName);
    boolean existsByLastName(String lastName);
}
