package com.project.backend_project.service.helper;

import org.springframework.stereotype.Service;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Format {

     // Validating Identification Number
    public void formatIdentificationNumber(Person person, PersonRequest personRequest){
    String identificationNumber = CleanAndValidate.cleanAndValidateNumber(personRequest.getIdentificationNumber(), "Identification Number");
        if(identificationNumber.length() != 20){
            throw new IllegalArgumentException("Identification Number must have 20 digits");
        }else {
            String part1 = identificationNumber.substring(0,8);
            String part2 = identificationNumber.substring(8,13);
            String part3 = identificationNumber.substring(13,18);
            String part4 = identificationNumber.substring(18,20);

            person.setIdentificationNumber(part1 + "-" + part2 + "-" + part3 + "-" + part4);
        }
    }

    // Validating contact
    public void formatContact(Person person, PersonRequest personRequest){
           String contact = CleanAndValidate.cleanAndValidateNumber(personRequest.getContact(), "Contact");
            if (contact.startsWith("0")){
                contact = "+255(0)" + contact.substring(1);
               }else if(!contact.startsWith("+255")){
                contact = "+255(0)" + contact;
            }
            person.setContact(contact);

    }

    // Validating date
    public void formatDateOfBirth(Person person, PersonRequest personRequest){
         String dateOfBirth = personRequest.getDateOfBirth();
        if(dateOfBirth != null){
            dateOfBirth = dateOfBirth.trim();
            if(!dateOfBirth.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$")){
                throw new IllegalArgumentException("Please enter the appropiate date format");
            }
        } 
        person.setDateOfBirth(dateOfBirth);   
    }
    
}
