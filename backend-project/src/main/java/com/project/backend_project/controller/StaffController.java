package com.project.backend_project.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.backend_project.dto.AuthenticationRequest;
import com.project.backend_project.dto.AuthenticationResponse;
import com.project.backend_project.dto.StaffRequest;
import com.project.backend_project.service.AuthenticationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staffs")
@SecurityRequirement(name = "bearerAuth")
public class StaffController {

    
    private final AuthenticationService service;

    @PostMapping("/create")
    public ResponseEntity<AuthenticationResponse> staffRegister(@RequestBody StaffRequest request){ 
        return ResponseEntity.ok(service.staffRequest(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> staffLogIn(@RequestBody AuthenticationRequest staffAuthentication){
        return ResponseEntity.ok(service.authenticate(staffAuthentication));
    }

     @PostMapping("/refresh-token")
    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
       service.refreshToken(request, response);
    }
  
}







