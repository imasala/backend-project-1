package com.project.backend_project.entities;

import java.time.Instant;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @Column( name = "metadata", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")   
    @JsonIgnore 
    private Staff staff;

    @JsonProperty("staffId")
    public Long getStaffId() {
        return staff != null ? staff.getId() : null;
    }

}