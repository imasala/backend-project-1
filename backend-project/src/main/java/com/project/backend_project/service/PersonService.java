package com.project.backend_project.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;
import com.project.backend_project.mapper.PersonMapper;
import com.project.backend_project.repository.PersonRepo;
import com.project.backend_project.service.helper.CleanAndValidate;
import com.project.backend_project.service.helper.Format;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;
    private final Format format;

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_STATUS = "status";

    public Map<String, Object> validation(@RequestBody PersonRequest personRequest){

        // Convert DTO to Entity
        Person person = PersonMapper.toEntity(personRequest);

        // Validate and Clean names
        String firstName = CleanAndValidate.cleanAndValidateName(personRequest.getFirstName(), "First Name");
        String middleName = CleanAndValidate.cleanAndValidateName(personRequest.getMiddleName(), "Middle Name");
        String lastName = CleanAndValidate.cleanAndValidateName(personRequest.getLastName(), "Last Name");
        String gender = CleanAndValidate.cleanAndValidateName(personRequest.getGender(), "Gender");
        String marriageStatus = CleanAndValidate.cleanAndValidateName(personRequest.getMarriageStatus(), "Marriage Status");
        String spouseName = CleanAndValidate.cleanAndValidateName(personRequest.getSpouseName(), "Spouse Name");
        String identificationType = CleanAndValidate.cleanAndValidateName(personRequest.getIdentificationType(), "Identification Type");
        String address = CleanAndValidate.cleanAndValidateName(personRequest.getAddress(), "Address");

        String email = personRequest.getEmail();
    
        // Validating Identification Number
        format.formatIdentificationNumber(person, personRequest);
        
        // Validating contact
        format.formatContact(person, personRequest);

        // Validating date
        format.formatDateOfBirth(person, personRequest);

        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setGender(gender);
        person.setMarriageStatus(marriageStatus);
        person.setSpouseName(spouseName);
        person.setIdentificationType(identificationType);
        person.setAddress(address);
        person.setEmail(email);

        personRepo.save(person);

        Map<String, Object> response = new HashMap<>();
        response.put(FIELD_STATUS, STATUS_SUCCESS);
        response.put(FIELD_MESSAGE,"Person is added successfully");

        return response;

    }

    public List<Person> getAllPerson(){
        if(personRepo.findAll().isEmpty()){
            throw new NoSuchElementException("No data found");
        }
        return personRepo.findAll();
    }

    // Fetching customer details by last name
    public Person search(String lastName){        
        return personRepo.findByLastName(lastName).orElse(null);
    }

    public Map<String, Object>  update(String lastName, PersonRequest updatePerson){
        Map<String, Object> responseBody = new HashMap<>();

        Person existingPerson = personRepo.findByLastName(lastName).orElse(null);

        if (existingPerson == null) {
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Person is not found!");
            return responseBody;
        }

        PersonMapper.updateEntity(existingPerson, updatePerson);
       
        // Save updated record in the database
        personRepo.save(existingPerson);
        
        // Create response
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully updated");

        return responseBody;
    }

    // Delete person
    public Map<String, Object> delete(String lastName){
        Map<String, Object> responseBody = new HashMap<>();

        if(!personRepo.existsByLastName(lastName)){
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Person is not found!");
            return responseBody;
        }

        personRepo.deleteByLastName(lastName);
        responseBody.put(FIELD_STATUS,STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully deleted");

        return responseBody;

    }
       
}
