package com.project.backend_project.controller;

import java.util.*;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;
import com.project.backend_project.repository.PersonRepo;
import com.project.backend_project.service.PersonService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Controller must have the responseEntity 
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PersonController {

    private final PersonRepo personRepo;
    private final PersonService personService;

    // Create and return response
    @PostMapping("/addReturn")
    public ResponseEntity< Map<String, Object> > createPerson(@RequestBody PersonRequest personRequest) {
        return ResponseEntity.ok(personService.validation(personRequest));
    }

    // Fetch all persons
    @GetMapping("/all")
    public List<Person> getAllPerson() {
        return personRepo.findAll();
    }

    // Fetch by ID
    @GetMapping("/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String lastName) {
       return ResponseEntity.ok(personService.search(lastName));
    }

    // Update existing person
    @PutMapping("/update/{lastName}")
    public ResponseEntity< Map<String, Object> > updatePerson(@PathVariable String lastName, @RequestBody PersonRequest updatePerson) {
         
       return  ResponseEntity.ok(personService.update(lastName, updatePerson));
    }

    
    @DeleteMapping("/delete/{lastName}")
    public ResponseEntity<Map<String, Object>> deletePerson(@PathVariable String lastName) {
       return personService.delete(lastName);
    }
}
