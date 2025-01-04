package com.spring_boot.HRMS.controller.hr;

import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.HRService;
import com.spring_boot.HRMS.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping()
    public String getDashboard(){
        return "Welcome to HRMS Application";
    }

    //Get HR profile by HR id
    @Operation(
            summary = "Get a HR by ID",
            description = "This endpoint retrieves a hr by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HR found"),
                    @ApiResponse(responseCode = "404", description = "HR not found")
            }
    )
    @GetMapping("/profile/{id}")
    public ResponseEntity<HR> getProfile(@PathVariable String id) throws Exception {
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
    @PutMapping("/profile/{id}")
    public ResponseEntity<HR> updateHrProfile(@PathVariable String id, @RequestBody HR hr) throws Exception {
        long hrId;
        try{
            hrId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try{
            return ResponseEntity.ok(hrService.updateHrProfile(hrId,hr));
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
                    @ApiResponse(responseCode = "200",description = "Job Details Found"),
                    @ApiResponse(responseCode = "404",description = "Job Details Not Found")
            }
    )
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(
            @Parameter(description = "Unique ID of the Job")
            @PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
        }catch (Exception e){
            throw new ProfileNotFoundException(e.getMessage());
        }
    }


    //Adding a JobPost
    @PostMapping("/post-job")
    public ResponseEntity<String> addJob(@RequestBody Job job) throws Exception {
        try {
           job= jobService.saveJob(job);
           return ResponseEntity.status(HttpStatus.CREATED).body("Job added successfully with id:"+job.getId()) ;
        }catch (Exception e){
            throw new Exception("can't post the job");
        }
    }

    //Updating JobPost with job id
    @PutMapping("/jobs/{id}")
    public ResponseEntity<String> updateJobPost(@PathVariable String id,@RequestBody Job job){
        try{
            jobService.updateJob(id,job);
            return ResponseEntity.status(HttpStatus.OK).body("Job with id "+id+" updated successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Delete a JobPost by id
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJobPostById(@PathVariable String id){
        try{
            jobService.deleteJobById(id);
            return ResponseEntity.status(HttpStatus.OK).body("deleted successfully.");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
