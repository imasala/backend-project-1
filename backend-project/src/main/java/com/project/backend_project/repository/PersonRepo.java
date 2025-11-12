package com.project.backend_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend_project.entities.Person;

public interface PersonRepo extends JpaRepository<Person, Long>{    
}
