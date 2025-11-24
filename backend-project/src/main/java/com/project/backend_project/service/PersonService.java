package com.project.backend_project.service;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.backend_project.dto.PersonRequest;
import com.project.backend_project.entities.Person;
import com.project.backend_project.repository.PersonRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepo personRepo;
    private static final String STATUS_SUCCESS = "success";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_STATUS = "status";

    private static final Map<String, Object> extraData = extraData();

    private static Map<String, Object> extraData() {
        Map<String, Object> map = new HashMap<>();

        map.put("addressCity", "Dodoma Mjini");
        map.put("addressFromDate", "01/04/2010");
        map.put("addressLine1", "Dodoma1");
        map.put("addressLine2", "Dodoma2");
        map.put("addressLine3", "Dodoma3");
        map.put("addressLine4", "Dodoma4");
        map.put("employAddressLine", "Dodoma5");
        map.put("employmentAddress", "Dodoma6");
        map.put("employmentCity", "Dodoma7");
        map.put("postalCode", "");
        map.put("idCityOfIssue", "Dar es salaam");
        map.put("idExpiryDate", "25/02/2030");
        map.put("idIssueDate", "25/02/2011");
        map.put("signature", "SGVsbG8sIFdvcmxkIQ==");
        map.put("idPhoto", "SGVsbG8sIFdvcmxkIQ==");
        map.put("employerName", null);
        map.put("employmentCategoryId", null);
        map.put("employmentStartMonth", null);
        map.put("employmentStartYear", null);
        map.put("titleId", 11);
        map.put("industryCode", "1116");
        map.put("marriageFlag", "S");
        map.put("addressPropertyTypeId", 353);
        map.put("addressTypeId", 41);
        map.put("countryOfBirthId", 676);
        map.put("countryOfIdIssue", 676);
        map.put("countryOfResidenceId", 676);
        map.put("customerCategory", "PER");
        map.put("customerSegmentId", 391);
        map.put("customerType", 718960);
        map.put("dependantCount", 0);
        map.put("employed", true);
        map.put("grossAnnualSalId", 384);
        map.put("identificationId", 403);
        map.put("identityType", "012");
        map.put("identityTypeId", 403);
        map.put("industryId", 263);
        map.put("marketingCampaignCd", "114");
        map.put("marketingCampaignId", 335);
        map.put("nationalityId", 261);
        map.put("occupationId", 425);
        map.put("openingReasonId", 491);
        map.put("primaryAddress", false);
        map.put("profQualificationCode", "018");
        map.put("profQualificationId", 308);
        map.put("professionCd", "018");
        map.put("professionId", 308);
        map.put("qualificationCode", "03");
        map.put("qualificationId", 431);
        map.put("religionId", 235);
        map.put("resident", true);
        map.put("riskCode", "P13");
        map.put("riskCountryId", 676);
        map.put("riskId", 566);
        map.put("serviceLevelId", 11);
        map.put("sourceOfFundCd", "231");
        map.put("sourceOfFundId", 231);
        map.put("sourceOfFundsId", 231);
        map.put("taxGroupCode", "200");
        map.put("taxGroupId", 382);
        map.put("taxStatusId", 441);
        map.put("verified", true);

        return map;
    }


    public Map<String, Object> validation(@RequestBody PersonRequest personRequest){

    Person person = new Person();

    //   Validate and Clean names
    String firstName = cleanAndValidateName(personRequest.getFirstName(), "First Name");
    String middleName = cleanAndValidateName(personRequest.getMiddleName(), "Middle Name");
    String lastName = cleanAndValidateName(personRequest.getLastName(), "Last Name");
    String gender = cleanAndValidateName(personRequest.getGender(), "Gender");
    String marriageStatus = cleanAndValidateName(personRequest.getMarriageStatus(), "Marriage Status");
    String spouseName = cleanAndValidateName(personRequest.getSpouseName(), "Spouse Name");
    String identificationType = cleanAndValidateName(personRequest.getIdentificationType(), "Identification Type");
    String address = cleanAndValidateName(personRequest.getAddress(), "Address");

    String email = personRequest.getEmail();
    
    // Validating Identification Number
    String identificationNumber = cleanAndValidateNumber(personRequest.getIdentificationNumber(), "Identification Number");
        if(identificationNumber.length() != 20){
            throw new IllegalArgumentException("Identification Number must have 20 digits");
        }else {
            String part1 = identificationNumber.substring(0,8);
            String part2 = identificationNumber.substring(8,13);
            String part3 = identificationNumber.substring(13,18);
            String part4 = identificationNumber.substring(18,20);

            person.setIdentificationNumber(part1 + "-" + part2 + "-" + part3 + "-" + part4);
        }


        // Validating contact
        String contact = cleanAndValidateNumber(personRequest.getContact(), "Contact");
            if (contact.startsWith("0")){
                contact = "+255(0)" + contact.substring(1);
               }else if(!contact.startsWith("+255")){
                contact = "+255(0)" + contact;
            }
            person.setContact(contact);

        // Validating date
        String dateOfBirth = personRequest.getDateOfBirth();
        if(dateOfBirth != null){
            dateOfBirth = dateOfBirth.trim();
            if(!dateOfBirth.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$")){
                throw new IllegalArgumentException("Please enter the appropiate date format");

            }
        }


        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setGender(gender);
        person.setMarriageStatus(marriageStatus);
        person.setSpouseName(spouseName);
        person.setIdentificationType(identificationType);
        person.setAddress(address);

        person.setDateOfBirth(dateOfBirth);
        person.setEmail(email);

        personRepo.save(person);

        Map<String, Object> response = new HashMap<>();
        response.put(FIELD_STATUS, STATUS_SUCCESS);
        response.put(FIELD_MESSAGE,"Person is added successfully");

        return response;

    }

// Helper Functions
    private String cleanAndValidateName(String field, String fieldName){
        if(field == null || field.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
            }
             field = field.trim().replaceAll("\\s+", " ");

            if(!field.matches("^[a-zA-Z ]+$")){
                throw new IllegalArgumentException(fieldName +" must contain only letters");
        }
        return field;
    }

    private String cleanAndValidateNumber(String value, String fieldName){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + "cannot be empty");
        }

        value = value.trim().replaceAll("\\s+", "");

        if(!value.matches("\\d+")){
            throw new IllegalArgumentException(fieldName + "must contain digits only");
        }

        return value;
    }

    
}
