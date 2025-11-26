package com.project.backend_project.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.backend_project.enums.Role;
import com.project.backend_project.dto.AuthenticationRequest;
import com.project.backend_project.dto.AuthenticationResponse;
import com.project.backend_project.dto.StaffRequest;
import com.project.backend_project.entities.*;
import com.project.backend_project.repository.StaffRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StaffRepo staffRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; 
    private final AuthenticationManager authenticationManager; 

    public AuthenticationResponse staffRequest(StaffRequest request){
        var staff = Staff.builder()
        .firstName(request.getFirstName())
        .middleName(request.getMiddleName())
        .lastName(request.getLastName())
        .domainEmail(request.getDomainEmail())
        .domainPassword(passwordEncoder.encode(request.getDomainPassword()))
        .role(Role.USER)
        .build();

        staffRepo.save(staff);

        var jwtToken = jwtService.generateToken(staff);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getDomainEmail(), 
                request.getDomainPassword())
        );

        var staff = staffRepo.findByDomainEmail(request.getDomainEmail()).orElseThrow(null);

        var jwtToken = jwtService.generateToken(staff);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    }

    
}
