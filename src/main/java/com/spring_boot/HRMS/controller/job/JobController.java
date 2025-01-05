package com.spring_boot.HRMS.controller.job;

import com.spring_boot.HRMS.dtos.JobDTO;
import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
@Slf4j
@Tag(name = "Job Controller",description = "Operations related to Job Controller")
public class JobController {

    private JobService jobService;

    //get all the jobs
    @Operation(
            summary = "Get All Jobs",
            description = "This endpoint retrieves all the Jobs",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Jobs Found"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error")
            }
    )
    @GetMapping()
    public ResponseEntity<List<JobDTO>>  getAllJobs(){
        return ResponseEntity.of(Optional.ofNullable(jobService.getAllJobs()));
    }


    //find a particular Job with id
    @Operation(
            summary = "Get a Job By Unique Job Id",
            description = "This endpoint retrieves a Job by an Unique Job Id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Job Found"),
                    @ApiResponse(responseCode = "404",description = "Job Not Found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(
            @Parameter(description = "Unique Job Id")
            @PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
        }catch (Exception e){
            throw new ProfileNotFoundException(e.getMessage());
        }
    }

    //Searching Jobs by JobTitle
    @GetMapping("/searchByTitle")
    public ResponseEntity<List<JobDTO>> searchJobsByJobTitle(@RequestParam("title") String title){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsByTitle(title));
    }

    //Searching Jobs by particular Skill Sets
    @GetMapping("/searchBySkills")
    public ResponseEntity<List<Job>> searchJobsBySkillSets(){
        return null;
    }

    //Searching Jobs by location
    @GetMapping("/searchByLocation")
    public  ResponseEntity<List<JobDTO>> searchJobByLocation(@RequestParam("location") String location){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsByLocation(location));
    }
}
