package com.spring_boot.HRMS.controller;

import com.spring_boot.HRMS.dtos.CandidatePostDTO;
import com.spring_boot.HRMS.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Public Controller",description = "This controller is used to registering an new Candidate")
public class PublicController {

    private final CandidateService candidateService;

    @Operation(
            summary = "Add a Candidate",
            description = "This endpoint post an new Candidate into DB",
            responses = {
                    @ApiResponse(responseCode = "201",description = "Candidate Created"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                    @ApiResponse(responseCode = "400",description = "BAD Request")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerCandidate(@RequestBody CandidatePostDTO candidate){
        try {
            candidateService.registerCandidate(candidate);
            return ResponseEntity.status(HttpStatus.CREATED).body("Candidate is registered successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
