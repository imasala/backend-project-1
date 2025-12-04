package com.project.backend_project.mapper;

import com.project.backend_project.dto.CustomerRequest;
import com.project.backend_project.entities.Customer;

public class CustomerMapper {

    private CustomerMapper(){}

     // Convert DTO to Entity
    public static Customer toEntity(CustomerRequest dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setMiddleName(dto.getMiddleName());
        customer.setLastName(dto.getLastName());
        customer.setGender(dto.getGender());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setMarriageStatus(dto.getMarriageStatus());
        customer.setSpouseName(dto.getSpouseName());
        customer.setContact(dto.getContact());
        customer.setIdentificationNumber(dto.getIdentificationNumber());
        customer.setIdentificationType(dto.getIdentificationType());
        customer.setAddress(dto.getAddress());
        customer.setEmail(dto.getEmail());

        return customer;
    }

    // Update existing entity with DTO: Contact, Identification Number, Date of Birth aren't included here as they are handled separately
    public static void updateEntity(Customer customer, CustomerRequest dto) {
        customer.setFirstName(dto.getFirstName());
        customer.setMiddleName(dto.getMiddleName());
        customer.setLastName(dto.getLastName());
        customer.setGender(dto.getGender());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setMarriageStatus(dto.getMarriageStatus());
        customer.setSpouseName(dto.getSpouseName());
        customer.setIdentificationType(dto.getIdentificationType());
        customer.setAddress(dto.getAddress());
    }

    // Convert Entity to DTO (optional, for responses)
    public static CustomerRequest toDto(Customer customer) {
        return new CustomerRequest(
            customer.getFirstName(),
            customer.getMiddleName(),
            customer.getLastName(),
            customer.getGender(),
            customer.getDateOfBirth(),
            customer.getMarriageStatus(),
            customer.getSpouseName(),
            customer.getContact(),
            customer.getIdentificationNumber(),
            customer.getIdentificationType(),
            customer.getAddress(),
            customer.getEmail()
        );
    }
}
