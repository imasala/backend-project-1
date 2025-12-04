package com.project.backend_project.controller;

import java.util.*;

import com.project.backend_project.dto.CustomerRequest;
import com.project.backend_project.entities.Customer;
import com.project.backend_project.service.CustomerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Controller must have the responseEntity 
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerService customerService;

    // Create and return response
    @PostMapping("/create")
    public ResponseEntity< Map<String, Object> > createPerson(@RequestBody CustomerRequest personRequest) {
        return ResponseEntity.ok(customerService.validation(personRequest));
    }

    // Fetch all persons
    @GetMapping("/fetch-all")
    public ResponseEntity<List<Customer>> getAllPerson() {
        return ResponseEntity.ok(customerService.getAllPerson());
    }

    // Fetch by last name
    @GetMapping("/fetch/{lastName}")
    public ResponseEntity<List<Customer>> getPersons(@PathVariable String lastName) {
       return ResponseEntity.ok(customerService.search(lastName));
    }

    // Update existing person
    @PatchMapping("/update/{id}")
    public ResponseEntity< Map<String, Object> > updatePerson(@PathVariable Long id, @RequestBody CustomerRequest updatePerson) {
  
       return  customerService.update(id, updatePerson);
    }

    // Delete person
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Map<String, Object>> deletePerson(@PathVariable String email) {
       return customerService.delete(email);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Map<String, String>> deleteAllPersons() {
      return customerService.deleteAll();
    }
}
