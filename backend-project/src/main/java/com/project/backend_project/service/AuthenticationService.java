package com.project.backend_project.service;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.backend_project.enums.Role;
import com.project.backend_project.enums.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.backend_project.dto.AuthenticationRequest;
import com.project.backend_project.dto.AuthenticationResponse;
import com.project.backend_project.dto.StaffRequest;
import com.project.backend_project.entities.*;
import com.project.backend_project.repository.StaffRepo;
import com.project.backend_project.repository.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenRepo tokenRepo;

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
        var refreshToken = jwtService.generateRefreshToken(staff);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
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
        var refreshToken = jwtService.generateRefreshToken(staff);
        revokeAllUserTokens(staff);
        saveUserToken(staff, jwtToken);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .build();
    }

    // Changes here
    private void saveUserToken(Staff staff, String jwtToken) {
        var token = Token.builder()
        .staff(staff)
        .accessToken(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
        tokenRepo.save(token);
    }

    // Changes here
    private void revokeAllUserTokens(Staff staff) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(staff.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
         final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                final String refreshToken;
                final String domainEmail;

                if(authHeader == null || !authHeader.startsWith("Bearer")){
                    return; // Stops execution if no token
                }
                refreshToken = authHeader.substring(7);
                domainEmail = jwtService.extractDomainEmail(refreshToken);
                if(domainEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    var userDetails = this.staffRepo.findByDomainEmail(domainEmail).orElseThrow();
                    if(jwtService.isTokenValid(refreshToken, userDetails)){

                        var accessToken = jwtService.generateToken(userDetails);
                        revokeAllUserTokens(userDetails);
                        saveUserToken(userDetails, accessToken);
                        var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                    new  ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                    }
                }



    }

    
}
