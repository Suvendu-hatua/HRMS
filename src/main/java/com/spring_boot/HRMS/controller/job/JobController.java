package com.spring_boot.HRMS.controller.job;

import com.spring_boot.HRMS.dtos.JobDTO;
import com.spring_boot.HRMS.exceptionHandling.ErrorResponse;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
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
                    @ApiResponse(responseCode = "200",description = "Job Found",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = JobDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "Job Not Found",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
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
    @Operation(
            summary = "Search Jobs By Job Title",
            description = "This endpoint retrieves jobs based on Job Title",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Jobs Found"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/searchByTitle")
    public ResponseEntity<List<JobDTO>> searchJobsByJobTitle(@RequestParam("title") String title){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsByTitle(title));
    }

    //Searching Jobs by particular Skill Sets
    @Operation(
            summary = "Search Jobs By Skill Sets",
            description = "This endpoint retrieves jobs based on Skill Sets",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Jobs Found"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/searchBySkills")
    public ResponseEntity<List<JobDTO>> searchJobsBySkillSets(@RequestParam("key")String key){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsBySkillSets(key));
    }

    //Searching Jobs by location
    @Operation(
            summary = "Search Jobs By Location",
            description = "This endpoint retrieves jobs based on searched Location",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Jobs Found"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "appplication/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/searchByLocation")
    public  ResponseEntity<List<JobDTO>> searchJobByLocation(@RequestParam("location") String location){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsByLocation(location));
    }
}
