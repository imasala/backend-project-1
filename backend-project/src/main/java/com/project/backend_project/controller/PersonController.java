package com.project.backend_project.controller;

import java.util.*;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;
import com.project.backend_project.service.PersonService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Controller must have the responseEntity 
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@SecurityRequirement(name = "bearerAuth")
public class PersonController {

    private final PersonService personService;

    // Create and return response
    @PostMapping("/create")
    public ResponseEntity< Map<String, Object> > createPerson(@RequestBody PersonRequest personRequest) {
        return ResponseEntity.ok(personService.validation(personRequest));
    }

    // Fetch all persons
    @GetMapping("/fetch-all")
    public ResponseEntity<List<Person>> getAllPerson() {
        return ResponseEntity.ok(personService.getAllPerson());
    }

    // Fetch by ID
    @GetMapping("/fetch/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String lastName) {
       return ResponseEntity.ok(personService.search(lastName));
    }

    // Update existing person
    @PutMapping("/update/{lastName}")
    public ResponseEntity< Map<String, Object> > updatePerson(@PathVariable String lastName, @RequestBody PersonRequest updatePerson) {
         
       return  ResponseEntity.ok(personService.update(lastName, updatePerson));
    }

    // Delete person
    @DeleteMapping("/delete/{lastName}")
    public ResponseEntity<Map<String, Object>> deletePerson(@PathVariable String lastName) {
       return ResponseEntity.ok(personService.delete(lastName));
    }
}
