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
    public ResponseEntity <Map<String, Object>>  update(Long id, CustomerRequest updateCustomer){
        Map<String, Object> responseBody = new HashMap<>();

        Customer existingPerson = customerRepo.findById(id).orElse(null);

        if (existingPerson == null) {
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Customer is not found!");
            return ResponseEntity.badRequest().body(responseBody);
        }

         String newEmail = updateCustomer.getEmail();
        if(newEmail != null && !newEmail.equals(existingPerson.getEmail())){
            existingPerson.setEmail(newEmail);
        }

        CleanAndValidate.cleanAndValidateName(updateCustomer.getFirstName(), "First Name");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getMiddleName(), "Middle Name");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getLastName(), "Last Name");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getGender(), "Gender");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getMarriageStatus(), "Marriage Status");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getSpouseName(), "Spouse Name");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getIdentificationType(), "Identification Type");
        CleanAndValidate.cleanAndValidateName(updateCustomer.getAddress(), "Address");

         // Validating Identification Number
         String newIdentificationNumber = updateCustomer.getIdentificationNumber();
         if(newIdentificationNumber != null && !newIdentificationNumber.equals(existingPerson.getIdentificationNumber())){
            format.formatIdentificationNumber(existingPerson, updateCustomer);
         }else{
            existingPerson.setIdentificationNumber(existingPerson.getIdentificationNumber());
         }
        
        // Validating contact
        String newContact = updateCustomer.getContact();
        if(newContact != null && !newContact.equals(existingPerson.getContact())){
        format.formatContact(existingPerson, updateCustomer);
        }else{
            existingPerson.setContact(existingPerson.getContact());
        }

        // Validating date
        // String newDateOfBirth = updateCustomer.getDateOfBirth();
        // if(newDateOfBirth != null && !newDateOfBirth.equals(existingPerson.getDateOfBirth())){
        //     format.formatDateOfBirth(existingPerson, updateCustomer);
        // }else{
        //     existingPerson.setDateOfBirth(existingPerson.getDateOfBirth());
        //     format.formatDateOfBirth(existingPerson, updateCustomer);
        // }
        if (updateCustomer.getDateOfBirth() != null){
        format.formatDateOfBirth(existingPerson, updateCustomer);
        }else{
            
        }


        CustomerMapper.updateEntity(existingPerson, updateCustomer);
       
        // Save updated record in the database
        customerRepo.save(existingPerson);
        
        // Create response
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
