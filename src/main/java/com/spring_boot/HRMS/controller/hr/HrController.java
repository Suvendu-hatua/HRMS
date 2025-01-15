package com.spring_boot.HRMS.controller.hr;

import com.spring_boot.HRMS.dtos.*;
import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ErrorResponse;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.HRService;
import com.spring_boot.HRMS.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hr")
@Slf4j
@Tag(name = "HR", description = "Operations related to HR")
public class HrController {

    private HRService hrService;
    private JobService jobService;

    @Autowired
    public HrController(HRService hrService,JobService jobService) {
        this.hrService = hrService;
        this.jobService=jobService;
    }

    //Get Dashboard
    @Operation(
            summary = "HR Dashboard"
    )
    @GetMapping()
    public String getDashboard(){
        return "Welcome to HRMS Application";
    }

    //Get HR profile by HR id
    @Operation(
            summary = "Get a HR by ID",
            description = "This endpoint retrieves a hr by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HR found" ,
                    content = @Content(
                            mediaType ="application/json",schema = @Schema(implementation = HrDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "HR not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(
                                    implementation = ErrorResponse.class
                            ))
                    )
            }
    )
    @GetMapping("/profile/{id}")
    public ResponseEntity<HrDTO> getProfile(
            @Parameter(description = "Unique Id of HR")
            @PathVariable String id) throws Exception {
        long hrId;
        try{
            hrId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try {
            return ResponseEntity.ok(hrService.getHrById(hrId));
        }catch (Exception e){
            throw new ProfileNotFoundException("can't find Hr profile with id:"+id);
        }
    }


    //Update HR profile by id
    @Operation(
            summary = "Update a HR Profile",
            description = "This endpoint update an existing HR Profile",
            responses = {
                    @ApiResponse(responseCode = "200",description = "HR Updated Successfully"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "400",description = "BAD Request",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PutMapping("/profile/{id}")
    public ResponseEntity<HR> updateHrProfile(@PathVariable String id, @RequestBody HrPostDTO hrPostDTO) throws Exception {
        long hrId;
        try{
            hrId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try{
            return ResponseEntity.ok(hrService.updateHrProfile(hrId,hrPostDTO));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    //find a particular Job with id
    @Operation(
            summary = "Get a Job details by JobID",
            description = "This endpoint retrieve a specific Job Details by an Unique JobID",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Job Details Found",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = JobDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "Job Details Not Found",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> getJobById(
            @Parameter(description = "Unique ID of the Job")
            @PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
        }catch (Exception e){
            throw new ProfileNotFoundException(e.getMessage());
        }
    }


    //Adding a JobPost
    @Operation(
            summary = "Post a Job",
            description = "This endpoint post a new Job into DB",
            responses = {
                    @ApiResponse(responseCode = "201",description = "Job Posted Successfully"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                    @ApiResponse(responseCode = "400",description = "BAD Request")
            }
    )
    @PostMapping("/post-job")
    public ResponseEntity<String> addJob(@RequestBody JobPostDTO jobPostDTO, Authentication authentication) throws Exception {
        try {
            String email=authentication.getName();
           Job job= jobService.saveJob(jobPostDTO,email);
           return ResponseEntity.status(HttpStatus.CREATED).body("Job added successfully with id:"+job.getId()) ;
        }catch (Exception e){
            throw new Exception("can't post the job");
        }
    }

    //Updating JobPost with job id
    @Operation(
            summary = "Update an Existing Job Post",
            description = "This endpoint update an existing JobPost",
            responses = {
                    @ApiResponse(responseCode = "200",description = "JobPost Updated Successfully"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                    @ApiResponse(responseCode = "400",description = "BAD Request")
            }
    )
    @PutMapping("/jobs/{id}")
    public ResponseEntity<String> updateJobPost(
            @Parameter(description = "Unique Job Id")
            @PathVariable String id,
            @RequestBody JobPostDTO job){
        try{
            jobService.updateJob(id,job);
            return ResponseEntity.status(HttpStatus.OK).body("Job with id "+id+" updated successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Delete a JobPost by id
    @Operation(
            summary = "Delete a Job by Job ID",
            description = "This endpoint delete an existing JobPost by an Unique Job Id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "JobPost deleted Successfully"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error")
            }
    )
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJobPostById(
            @Parameter(description = "Unique Job Id")
            @PathVariable String id){
        try{
            jobService.deleteJobById(id);
            return ResponseEntity.status(HttpStatus.OK).body("deleted successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get all Posted Jobs By a HR",
            description = "This endpoint retrieves all the Jobs posted by a HR",
            responses = {
                    @ApiResponse(responseCode = "200",description = "List of Jobs Found"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/job-posts")
    public ResponseEntity<List<JobDTO>> getAllJobPosts(Authentication authentication){
        //getting username
        String email=authentication.getName();
        try{
            return ResponseEntity.status(HttpStatus.OK).body( hrService.findAllJobPosts(email));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get All Applied Candidates",
            description = "This endpoint retrieves all the Candidates who have applied to this particular Job",
            responses = {
                    @ApiResponse(responseCode = "200",description = "List of Applied Candidates Found"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    @GetMapping("/applied-candidates/{jobId}")
    public ResponseEntity<List<CandidateDTO>> getAllAppliedCandidates(
            @Parameter(description = "Unique Job Id")
            @PathVariable String jobId){
        //Get all the candidates who have applied to this job
        try{
            return ResponseEntity.status(HttpStatus.OK).body(hrService.findAllJobAppliedCandidates(jobId));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
