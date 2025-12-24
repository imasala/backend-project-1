package com.project.backend_project.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.backend_project.repository.StaffRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {
    
    private final StaffRepo staffRepo;

    public Map<String, Object> deleteStaff(Long id){
    var staff = staffRepo.findById(id);

    if(staff.isPresent()){
        staffRepo.delete(staff.get());
        return Map.of(
            "status", "Success",
            "message", "Staff deleted successfully"
        );
    } else {
        throw new IllegalArgumentException("Staff not found"); 
    }
}
    
}
