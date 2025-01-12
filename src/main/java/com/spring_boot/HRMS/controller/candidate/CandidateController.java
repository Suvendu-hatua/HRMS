package com.spring_boot.HRMS.controller.candidate;

import com.spring_boot.HRMS.dtos.CandidateDTO;
import com.spring_boot.HRMS.dtos.CandidatePostDTO;
import com.spring_boot.HRMS.exceptionHandling.ErrorResponse;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Candidate",description = "Operations Related to Candidate")
public class CandidateController {

    private CandidateService candidateService;

    @Operation(
            summary = "Get Candidate by Unique Candidate Id",
            description = "This endpoint retrieve Candidate details by an unique ID",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Candidate Found",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = CandidateDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "Candidate Not Found",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable String id){
        long candidateId;
        try{
            candidateId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(id+" can't be converted into a long value.");
        }
        //success
        try{
            CandidateDTO candidate=candidateService.getCandidateById(candidateId);
            return ResponseEntity.status(HttpStatus.OK).body(candidate);
        }catch (Exception e){
            throw new ProfileNotFoundException(e.getMessage());
        }
    }

    //Update Candidate Account
    @Operation(
            summary = "Update Candidate Profile",
            description = "This endpoint updates an existing Candidate Profile",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Candidate Updated Successfully"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCandidateAccount(@PathVariable String id, @RequestBody CandidatePostDTO candidatePostDTO){
        long candidateId;
        try{
            candidateId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(id+" can't be converted into a long value.");
        }
        //Success
        try{
            candidateService.updateCandidate(candidateId,candidatePostDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //applying to a specific Job
    @Operation(
            summary = "Apply To a Job",
            description = "This endpoint will help a candidate to apply to a Specific Job with  Specific JobID",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Applied to Job"),
                    @ApiResponse(responseCode = "400",description = "BAD Request"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error")
            }
    )
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
