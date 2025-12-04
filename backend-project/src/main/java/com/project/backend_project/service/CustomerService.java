package com.project.backend_project.service;

import java.util.*;

import org.springframework.http.ResponseEntity;
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

    public Map<String, Object> validation(@RequestBody CustomerRequest customerRequest){

        // Convert DTO to Entity
        Customer customer = CustomerMapper.toEntity(customerRequest);

        // Validate and Clean names
        String firstName = CleanAndValidate.cleanAndValidateName(customerRequest.getFirstName(), "First Name");
        String middleName = CleanAndValidate.cleanAndValidateName(customerRequest.getMiddleName(), "Middle Name");
        String lastName = CleanAndValidate.cleanAndValidateName(customerRequest.getLastName(), "Last Name");
        String gender = CleanAndValidate.cleanAndValidateName(customerRequest.getGender(), "Gender");
        String marriageStatus = CleanAndValidate.cleanAndValidateName(customerRequest.getMarriageStatus(), "Marriage Status");
        String spouseName = CleanAndValidate.cleanAndValidateName(customerRequest.getSpouseName(), "Spouse Name");
        String identificationType = CleanAndValidate.cleanAndValidateName(customerRequest.getIdentificationType(), "Identification Type");
        String address = CleanAndValidate.cleanAndValidateName(customerRequest.getAddress(), "Address");

        String email = customerRequest.getEmail();
    
        // Validating Identification Number
        format.formatIdentificationNumber(customer, customerRequest);
        
        // Validating contact
        format.formatContact(customer, customerRequest);

        // Validating date
        format.formatDateOfBirth(customer, customerRequest);

        customer.setFirstName(firstName);
        customer.setMiddleName(middleName);
        customer.setLastName(lastName);
        customer.setGender(gender);
        customer.setMarriageStatus(marriageStatus);
        customer.setSpouseName(spouseName);
        customer.setIdentificationType(identificationType);
        customer.setAddress(address);
        customer.setEmail(email);
        

        customerRepo.save(customer);

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
    public ResponseEntity <Map<String, Object>>  update(Long id, CustomerRequest updateCustomerRequest){
        Map<String, Object> responseBody = new HashMap<>();

        Customer existingPerson = customerRepo.findById(id).orElse(null);

        if (existingPerson == null) {
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Customer is not found!");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if(updateCustomerRequest.getEmail() != null){
            existingPerson.setEmail(updateCustomerRequest.getEmail());
        }

        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getFirstName(), "First Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getMiddleName(), "Middle Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getLastName(), "Last Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getGender(), "Gender");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getMarriageStatus(), "Marriage Status");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getSpouseName(), "Spouse Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getIdentificationType(), "Identification Type");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getAddress(), "Address");

         // Validating Identification Number
         if(updateCustomerRequest.getIdentificationNumber()!= null){
            format.formatIdentificationNumber(existingPerson, updateCustomerRequest);
         }else{
            existingPerson.setIdentificationNumber(existingPerson.getIdentificationNumber());
         }  

        // Validating contact
        if (updateCustomerRequest.getContact() != null) {
        format.formatContact(existingPerson, updateCustomerRequest);  
        }else{
            existingPerson.setContact(existingPerson.getContact());
        }

        // Validating date
        if (updateCustomerRequest.getDateOfBirth() != null){
        format.formatDateOfBirth(existingPerson, updateCustomerRequest);
        }

        CustomerMapper.updateEntity(existingPerson, updateCustomerRequest);
       
        // Save updated record in the database
        customerRepo.save(existingPerson);
        
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully updated");
        return ResponseEntity.ok(responseBody);
    }

    // Delete person
    public ResponseEntity<Map<String, Object>> delete(String lastName){
        Map<String, Object> responseBody = new HashMap<>();

        if(!customerRepo.existsByLastName(lastName)){
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Customer is not found!");
            return ResponseEntity.badRequest().body(responseBody);
        }

        customerRepo.deleteByLastName(lastName);
        responseBody.put(FIELD_STATUS,STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully deleted");

        return ResponseEntity.ok(responseBody);

    }

    // Delete all persons
    public ResponseEntity<Map<String, String>> deleteAll() {
        customerRepo.deleteAll();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "All Customer details have been deleted successfully.");

        return ResponseEntity.ok(responseBody);
    }
       
}
