package com.spring_boot.HRMS.controller;

import com.spring_boot.HRMS.entity.Candidate;
import com.spring_boot.HRMS.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublicController {

    private final CandidateService candidateService;
    private  final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerCandidate(@RequestBody Candidate candidate){
        try {
            //defining candidate role in the Person Table
            candidate.getPerson().setRole("ROLE_CANDIDATE");
            //Encrypting candidate password in the Person Table
            candidate.getPerson().setPassword(passwordEncoder.encode(candidate.getPerson().getPassword()));
            candidateService.registerCandidate(candidate);
            return ResponseEntity.status(HttpStatus.CREATED).body("Candidate is registered successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
