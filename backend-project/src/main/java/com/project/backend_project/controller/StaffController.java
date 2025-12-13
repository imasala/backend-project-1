package com.project.backend_project.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.backend_project.dto.AuthenticationRequest;
import com.project.backend_project.dto.AuthenticationResponse;
import com.project.backend_project.dto.StaffRequest;
import com.project.backend_project.service.AuthenticationService;
import com.project.backend_project.service.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staffs")
@SecurityRequirement(name = "bearerAuth")
public class StaffController {

    
    private final AuthenticationService service;
    private final StaffService staffService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/auth/create")
    public ResponseEntity<Map<String, Object>> staffRegister(@RequestBody StaffRequest request){ 
        return ResponseEntity.ok(service.staffRequest(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> staffLogIn(@RequestBody AuthenticationRequest staffAuthentication){
        return ResponseEntity.ok(service.authenticate(staffAuthentication));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteStaff(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.deleteStaff(id));
    }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponse> refreshToken(
        HttpServletRequest request
       ) {
     return ResponseEntity.ok(service.refreshToken(request));
    }

}







