package com.project.backend_project.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String domainEmail;
    private String domainPassword;

}
