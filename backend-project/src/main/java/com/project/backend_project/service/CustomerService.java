package com.project.backend_project.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.backend_project.dto.CustomerRequest;
import com.project.backend_project.entities.Customer;
import com.project.backend_project.mapper.CustomerMapper;
import com.project.backend_project.repository.CustomerRepo;
import com.project.backend_project.service.helper.CleanAndValidate;
import com.project.backend_project.service.helper.Format;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final Format format;

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_STATUS = "status";

    public Map<String, Object> validation(@RequestBody CustomerRequest personRequest){

        // Convert DTO to Entity
        Customer person = CustomerMapper.toEntity(personRequest);

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

        customerRepo.save(person);

        Map<String, Object> response = new HashMap<>();
        response.put(FIELD_STATUS, STATUS_SUCCESS);
        response.put(FIELD_MESSAGE,"Customer is added successfully");

        return response;

    }

    
    public List<Customer> getAllPerson(){
        if(customerRepo.findAll().isEmpty()){
            throw new NoSuchElementException("No data found");
        }
        return customerRepo.findAll();
    }

    // Fetching customer details by last name
    public List<Customer> search(String lastName){        
        return customerRepo.findByLastName(lastName);
    }

    // Updating person details
    public Map<String, Object>  update(String email, CustomerRequest updatePerson){
        Map<String, Object> responseBody = new HashMap<>();

        Customer existingPerson = customerRepo.findByEmail(email).orElse(null);

        if (existingPerson == null) {
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Customer is not found!");
            return responseBody;
        }

        CustomerMapper.updateEntity(existingPerson, updatePerson);
       
        // Save updated record in the database
        customerRepo.save(existingPerson);
        
        // Create response
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully updated");

        return responseBody;
    }

    // Delete person
    public Map<String, Object> delete(String lastName){
        Map<String, Object> responseBody = new HashMap<>();

        if(!customerRepo.existsByLastName(lastName)){
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Customer is not found!");
            return responseBody;
        }

        customerRepo.deleteByLastName(lastName);
        responseBody.put(FIELD_STATUS,STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully deleted");

        return responseBody;

    }

    // Delete all persons
    public Map<String, String> deleteAll() {
        customerRepo.deleteAll();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "All Customer details have been deleted successfully.");

        return responseBody;
    }
       
}
