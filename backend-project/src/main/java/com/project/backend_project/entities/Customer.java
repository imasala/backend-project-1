package com.project.backend_project.entities;

import jakarta.persistence.*;
import lombok.Data;

 
@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;
    
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String marriageStatus;

    private String spouseName;

    private String contact;

    @Column(nullable = false)
    private String identificationNumber;

    @Column(nullable = false)
    private String identificationType;

    @Column(nullable = false)
    private String address;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

}