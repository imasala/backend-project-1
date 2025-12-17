package com.project.backend_project.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.backend_project.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="staff")
public class Staff implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Email(message = "Email must be valid")
    private String domainEmail;

    private String domainPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(
    mappedBy = "staff",
    cascade = CascadeType.REMOVE,
    orphanRemoval = true
    )
    private List<Token> tokens = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
