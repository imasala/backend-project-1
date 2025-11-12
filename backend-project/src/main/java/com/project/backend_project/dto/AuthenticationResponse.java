package com.project.backend_project.dto;

import lombok.*;

@Data
@Builder
public class AuthenticationResponse {

    private String token;  
}
