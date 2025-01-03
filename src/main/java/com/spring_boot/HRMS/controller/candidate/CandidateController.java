package com.spring_boot.HRMS.controller.candidate;

import com.spring_boot.HRMS.entity.Candidate;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.CandidateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

    private CandidateService candidateService;

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable String id){
        long candidateId;
        try{
            candidateId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(id+" can't be converted into a long value.");
        }
        //success
        try{
            Candidate candidate=candidateService.getCandidateById(candidateId);
            return ResponseEntity.status(HttpStatus.OK).body(candidate);
        }catch (Exception e){
            throw new ProfileNotFoundException(e.getMessage());
        }
    }

    //Update Candidate Account
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCandidateAccount(@PathVariable String id, @RequestBody Candidate candidate){
        long candidateId;
        try{
            candidateId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(id+" can't be converted into a long value.");
        }
        //Success
        try{
            candidateService.updateCandidate(candidateId,candidate);
            return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //applying to a specific Job
    @PostMapping("/do-apply/{jobId}")
    public ResponseEntity<String> applyToJob(@PathVariable String jobId, Authentication authentication){
       try{
           //Retrieving logged-in user's email
           String email=authentication.getName();
           //applying to the Job
           candidateService.applyToJob(email, jobId);
           return ResponseEntity.status(HttpStatus.OK).body("Successful");
       }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
    }
}
