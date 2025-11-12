package com.project.backend_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

 
@Entity
@Data
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Size(min=3, message="First name must have at least more 3 characters")
    private String firstName;

    @NotBlank(message = "Middle name is required")
    @Size(min=3, message="Middle name must have at least more 3 characters")
    private String middleName;
    
    @NotBlank(message = "Last name is required")
    @Size(min=3, message="Last name must have at least more 3 characters")
    private String lastName;

    @NotBlank(message = "Gender is required")
    private String gender;

    // @NotBlank(message = "Date of Birth is required")
    @Pattern(regexp ="^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$", message = "Date must be in the format DD/MM/YYYY")
    private String dateOfBirth;

    private String marriageStatus;
    private String spouseName;

    @NotBlank(message = "Contact is required")
    private String contact;

    
    @NotBlank(message = "Identification Number is required")
    private String identificationNumber;

    @NotBlank(message = "Identification Type is required")
    private String identificationType;

    @NotBlank(message = "Address is required")
    private String address;

    @Email(message = "Email must be valid")
    private String email;
}