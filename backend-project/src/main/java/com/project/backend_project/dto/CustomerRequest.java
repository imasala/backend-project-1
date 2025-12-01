package com.project.backend_project.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String marriageStatus;
    private String spouseName;
    private String contact;
    private String identificationNumber;
    private String identificationType;
    private String address;
    private String email;

}
