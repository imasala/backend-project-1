package com.project.backend_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend_project.entities.Person;
import java.util.List;
import java.util.Optional;


public interface PersonRepo extends JpaRepository<Person, Long>{    

    List<Person> findByLastName(String lastName); 
    Optional<Person> findByEmail(String email);
    void deleteByLastName(String lastName);
    boolean existsByLastName(String lastName);
}
