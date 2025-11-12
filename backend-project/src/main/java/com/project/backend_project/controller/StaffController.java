package com.project.backend_project.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.backend_project.dto.AuthenticationRequest;
import com.project.backend_project.dto.AuthenticationResponse;
import com.project.backend_project.dto.StaffRequest;
import com.project.backend_project.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class StaffController {

    
    private final AuthenticationService service;

    @PostMapping("/api/staff/addReturn")
    public ResponseEntity<AuthenticationResponse> staffRegister(@RequestBody StaffRequest request){ 
        return ResponseEntity.ok(service.staffRequest(request));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthenticationResponse> staffLogIn(@RequestBody AuthenticationRequest staffAuthentication){
        return ResponseEntity.ok(service.authenticate(staffAuthentication));
    }

  
}







