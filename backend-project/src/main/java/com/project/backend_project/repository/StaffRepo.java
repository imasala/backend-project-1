package com.project.backend_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend_project.entities.Staff;
import java.util.*;


public interface StaffRepo extends JpaRepository <Staff, Long>{
   
    Optional<Staff> findByDomainEmail(String domainEmail);
}
