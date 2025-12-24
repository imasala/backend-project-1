package com.project.backend_project.service;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.backend_project.dto.CustomerRequest;
import com.project.backend_project.entities.Customer;
import com.project.backend_project.entities.Staff;
import com.project.backend_project.mapper.CustomerMapper;
import com.project.backend_project.repository.CustomerRepo;
import com.project.backend_project.repository.StaffRepo;
import com.project.backend_project.service.helper.CleanAndValidate;
import com.project.backend_project.service.helper.Format;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final StaffRepo staffRepo;
    private final Format format;

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_STATUS = "status";

    public Map<String, Object> validation(@RequestBody CustomerRequest customerRequest){

         // Get logged-in staff email
            String loggedEmail = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

         // Fetch Staff entity
            Staff staff = staffRepo.findByDomainEmail(loggedEmail)
                    .orElseThrow(() -> new RuntimeException("Staff not found"));

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

        customer.setStaff(staff);

        customer.setMetadata(Metadata.extraData);
        

        customerRepo.save(customer);

        Map<String, Object> response = new HashMap<>();
        response.put(FIELD_STATUS, STATUS_SUCCESS);
        response.put(FIELD_MESSAGE,"Customer is added successfully");

        return response;

    }

    
    public List<Customer> getAllPerson(){
        if(customerRepo.findAllByOrderByCreatedAtDesc().isEmpty()){
            throw new NoSuchElementException("No data found");
        }
        return customerRepo.findAllByOrderByCreatedAtDesc();
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

        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getFirstName(), "First Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getMiddleName(), "Middle Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getLastName(), "Last Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getGender(), "Gender");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getMarriageStatus(), "Marriage Status");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getSpouseName(), "Spouse Name");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getIdentificationType(), "Identification Type");
        CleanAndValidate.cleanAndValidateName(updateCustomerRequest.getAddress(), "Address");

         // Validating Identification Number
         if(updateCustomerRequest.getIdentificationNumber()!= null && 
            !updateCustomerRequest.getIdentificationNumber().equals(existingPerson.getIdentificationNumber())){
            format.formatIdentificationNumber(existingPerson, updateCustomerRequest);
         } 

        // Validating contact
        if (updateCustomerRequest.getContact() != null && 
        !updateCustomerRequest.getContact().equals(existingPerson.getContact())
        ) {
        format.formatContact(existingPerson, updateCustomerRequest);  
        }

        // Validating date
        if (updateCustomerRequest.getDateOfBirth() != null){
        format.formatDateOfBirth(existingPerson, updateCustomerRequest);
        }

        // Validating email
        if(updateCustomerRequest.getEmail() != null){
            String newEmail = updateCustomerRequest.getEmail();
            String oldEmail = existingPerson.getEmail();

            if(!newEmail.equalsIgnoreCase(oldEmail) && customerRepo.existsByEmail(newEmail)){
                    throw new IllegalArgumentException("Email is already in use");
            }
            existingPerson.setEmail(newEmail);
        }

        CustomerMapper.updateEntity(existingPerson, updateCustomerRequest);

        existingPerson.setMetadata(Metadata.extraData);

        // Save updated record in the database
        customerRepo.save(existingPerson);
        
        responseBody.put(FIELD_STATUS, STATUS_SUCCESS);
        responseBody.put(FIELD_MESSAGE, "Data is successfully updated");
        return ResponseEntity.ok(responseBody);
    }

    // Delete person
    public ResponseEntity<Map<String, Object>> delete(Long id){
        Map<String, Object> responseBody = new HashMap<>();

        if(!customerRepo.existsById(id)){
            responseBody.put(FIELD_STATUS, STATUS_ERROR);
            responseBody.put(FIELD_MESSAGE, "Customer is not found!");
            return ResponseEntity.badRequest().body(responseBody);
        }

        customerRepo.deleteById(id);
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
