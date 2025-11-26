package com.project.backend_project.mapper;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;

public class PersonMapper {

    private PersonMapper(){}

     // Convert DTO to Entity
    public static Person toEntity(PersonRequest dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        person.setGender(dto.getGender());
        person.setDateOfBirth(dto.getDateOfBirth());
        person.setMarriageStatus(dto.getMarriageStatus());
        person.setSpouseName(dto.getSpouseName());
        person.setContact(dto.getContact());
        person.setIdentificationNumber(dto.getIdentificationNumber());
        person.setIdentificationType(dto.getIdentificationType());
        person.setAddress(dto.getAddress());
        person.setEmail(dto.getEmail());
        return person;
    }

    // Update existing entity with DTO
    public static void updateEntity(Person person, PersonRequest dto) {
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        person.setGender(dto.getGender());
        person.setDateOfBirth(dto.getDateOfBirth());
        person.setMarriageStatus(dto.getMarriageStatus());
        person.setSpouseName(dto.getSpouseName());
        person.setContact(dto.getContact());
        person.setIdentificationNumber(dto.getIdentificationNumber());
        person.setIdentificationType(dto.getIdentificationType());
        person.setAddress(dto.getAddress());
        person.setEmail(dto.getEmail());
    }

    // Convert Entity to DTO (optional, for responses)
    public static PersonRequest toDto(Person person) {
        return new PersonRequest(
            person.getFirstName(),
            person.getMiddleName(),
            person.getLastName(),
            person.getGender(),
            person.getDateOfBirth(),
            person.getMarriageStatus(),
            person.getSpouseName(),
            person.getContact(),
            person.getIdentificationNumber(),
            person.getIdentificationType(),
            person.getAddress(),
            person.getEmail()
        );
    }
}
