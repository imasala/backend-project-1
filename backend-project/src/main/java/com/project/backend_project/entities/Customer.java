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
        
    private String firstName;

    private String middleName;
    
    private String lastName;

    private String gender;

    private String dateOfBirth;

    private String marriageStatus;

    private String spouseName;

    private String contact;
    
    private String identificationNumber;

    private String identificationType;

    private String address;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

}