package com.spring_boot.HRMS.controller.job;

import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    private JobService jobService;

    //get all the jobs
    @GetMapping()
    public ResponseEntity<List<Job>>  getAllJobs(){
        return ResponseEntity.of(Optional.ofNullable(jobService.getAllJobs()));
    }
    //find a particular Job with id
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
        }catch (Exception e){
            throw new ProfileNotFoundException(e.getMessage());
        }
    }

    //Searching Jobs by JobTitle
    @GetMapping("/searchByTitle")
    public ResponseEntity<List<Job>> searchJobsByJobTitle(@RequestParam("title") String title){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsByTitle(title));
    }

    //Searching Jobs by particular Skill Sets
    @GetMapping
    public ResponseEntity<List<Job>> searchJobsBySkillSets(){
        return null;
    }

    //Searching Jobs by location
    @GetMapping("/searchByLocation")
    public  ResponseEntity<List<Job>> searchJobByLocation(@RequestParam("location") String location){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.findJobsByLocation(location));
    }
}
