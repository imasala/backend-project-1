package com.project.backend_project.controller;

import java.util.*;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;
import com.project.backend_project.repository.PersonRepo;
import com.project.backend_project.service.PersonService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PersonController {

    private final PersonRepo personRepo;
    private final PersonService personService;
    

    private static final String STATUS_SUCCESS = "success";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_STATUS = "status";


    // Create and return response
    @PostMapping("/addReturn")
    public ResponseEntity< Map<String, Object> > createPerson(@RequestBody PersonRequest personRequest) {
        
      var response = personService.validation(personRequest);
        return ResponseEntity.ok(response);
    }

    // Fetch all persons
    @GetMapping("/all")
    public List<Person> getAllPerson() {
        return personRepo.findAll();
    }

    // Fetch by ID
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Person person = personRepo.findById(id).orElse(null);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(person);
    }

    // Update existing person
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updatePerson(@PathVariable Long id, @RequestBody PersonRequest updatePerson) {
         Person existingPerson = personRepo.findById(id).orElse(null);

        if (existingPerson == null) {
            return ResponseEntity.notFound().build();
        }

        // Update data 
        existingPerson.setFirstName(updatePerson.getFirstName());
        existingPerson.setMiddleName(updatePerson.getMiddleName());
        existingPerson.setLastName(updatePerson.getLastName());
        existingPerson.setGender(updatePerson.getGender());
        existingPerson.setDateOfBirth(updatePerson.getDateOfBirth());
        existingPerson.setMarriageStatus(updatePerson.getMarriageStatus());
        existingPerson.setSpouseName(updatePerson.getSpouseName());
        existingPerson.setContact(updatePerson.getContact());
        existingPerson.setIdentificationNumber(updatePerson.getIdentificationNumber());
        existingPerson.setIdentificationType(updatePerson.getIdentificationType());
        existingPerson.setAddress(updatePerson.getAddress());
        existingPerson.setEmail(updatePerson.getEmail());

        // Save updated record in the database
        personRepo.save(existingPerson);
        
        // Create response
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully updated");

       

        return ResponseEntity.ok(responseBody);
       
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deletePerson(@PathVariable Long id) {
        if(!personRepo.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        personRepo.deleteById(id);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(FIELD_STATUS,STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully deleted");

        return ResponseEntity.ok(responseBody);
    }
}
