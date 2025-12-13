package com.project.backend_project.service;


import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.backend_project.enums.Role;
import com.project.backend_project.enums.TokenType;
import com.project.backend_project.dto.AuthenticationRequest;
import com.project.backend_project.dto.AuthenticationResponse;
import com.project.backend_project.dto.StaffRequest;
import com.project.backend_project.entities.*;
import com.project.backend_project.repository.StaffRepo;
import com.project.backend_project.repository.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenRepo tokenRepo;

    private final StaffRepo staffRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; 
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> staffRequest(StaffRequest request){

        Role role = request.getRole();

        if(role == null){
            role = Role.USER; // Default role
        }

        if(role != Role.ADMIN && role != Role.USER){
            throw new IllegalArgumentException("Invalid role specified");
        }


        var staff = Staff.builder()
        .firstName(request.getFirstName())
        .middleName(request.getMiddleName())
        .lastName(request.getLastName())
        .domainEmail(request.getDomainEmail())
        .domainPassword(passwordEncoder.encode(request.getDomainPassword()))
        .role(role)
        .build();

        staffRepo.save(staff);

        return Map.of(
            "status", "Success",
            "message", "Staff registered successfully",
            "role", role.name()
        );

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getDomainEmail(), 
                request.getDomainPassword())
        );

        var staff = staffRepo.findByDomainEmail(request.getDomainEmail()).orElseThrow(null);

        var jwtToken = jwtService.generateToken(staff);
        var refreshToken = jwtService.generateRefreshToken(staff);
        revokeAllUserTokens(staff);
        saveUserToken(staff, jwtToken);
        return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
    }

    // Changes here
    private void saveUserToken(Staff staff, String jwtToken) {
        var accessToken = Token.builder()
        .staff(staff)
        .accessToken(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
        tokenRepo.save(accessToken);
    }

    // Changes here
    private void revokeAllUserTokens(Staff staff) {

        var validUserTokens = tokenRepo.findAllValidTokenByUser(staff.getId());

        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(accessToken -> {
            accessToken.setExpired(true);
            accessToken.setRevoked(true);
        });
        
        tokenRepo.saveAll(validUserTokens);
    }

    // :::::::::::::::

   public AuthenticationResponse refreshToken(HttpServletRequest request) {

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return null; // controller will return 200 with null (or we can change that)
    }

    String refreshToken = authHeader.substring(7);
    String domainEmail = jwtService.extractDomainEmail(refreshToken);

    if (domainEmail == null) {
        return null;
    }

    var user = staffRepo.findByDomainEmail(domainEmail).orElseThrow();

    if (!jwtService.isTokenValid(refreshToken, user)) {
        return null;
    }

    // Generate new access accessToken
    String newAccessToken = jwtService.generateToken(user);

    // Update accessToken repository
    revokeAllUserTokens(user);
    saveUserToken(user, newAccessToken);

    // Return everything to controller
    return AuthenticationResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(refreshToken)
            .build();
}

    
}
