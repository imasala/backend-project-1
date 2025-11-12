package com.project.backend_project.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequest {
    private String firstName;
    private String middleName;   
    private String lastName;
    private String domainEmail;
    private String domainPassword;

}
