package com.spring_boot.HRMS.controller.hr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class Dashboard {

    @GetMapping()
    public String getDashboard(){
        return "Welcome to HRMS Application";
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        return null;
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateHrProfile(@PathVariable String id){
        return null;
    }
}
