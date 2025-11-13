package com.project.backend_project.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.backend_project.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="staff") //This is a staff table
public class Staff implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min=3, message="First name must have at least more 3 characters")
    private String firstName;

    @NotBlank(message = "Middle name is required")
    @Size(min=3, message="Middle name must have at least more 3 characters")
    private String middleName;
    
    @NotBlank(message = "Last name is required")
    @Size(min=3, message="Last name must have at least more 3 characters")
    private String lastName;

    @Email(message = "Email must be valid")
    private String domainEmail;

    private String domainPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return domainPassword;
    }

    @Override
    public String getUsername() {
        return domainEmail;
    }

    
}
