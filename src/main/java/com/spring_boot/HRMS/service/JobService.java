package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.HrDao;
import com.spring_boot.HRMS.dao.JobDao;
import com.spring_boot.HRMS.dtos.JobDTO;
import com.spring_boot.HRMS.dtos.JobPostDTO;
import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.mapper.JobMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobService {
    private final HrDao hrDao;
    private final JobDao jobDao;
    private final JobMapper jobMapper;

    public List<JobDTO> getAllJobs(){
        return jobDao.findAll().stream().map(jobMapper::toDTO).collect(Collectors.toList());
    }

    public JobDTO getJobById(String id){
        Job job= jobDao.findById(id).orElseThrow(()->new ProfileNotFoundException("can't find Job by id:"+id));
        //Converting to JobDTO
        return jobMapper.toDTO(job);
    }

    @Transactional
    public Job saveJob(JobPostDTO jobPostDTO,String email){
        //retrieving HR details by  authenticate user's email
        HR hr=hrDao.findByPersonEmail(email).orElseThrow(()->new ProfileNotFoundException("can't find HR profile with email:"+email));
        //Converting to HR
        Job job=jobMapper.toEntity(jobPostDTO);
        //generating random ID
        job.setId(UUID.randomUUID().toString());
        //Setting Current DateTime for publishedDate
        LocalDate localDate=LocalDate.now();
        job.setPublishedDate(localDate);
        //attaching Hr to this job
        job.setHr(hr);
        //Saving Job to Database.
        return jobDao.save(job);
    }

    @Transactional
    public Job updateJob(String id,JobPostDTO jobPostDTO){
        if(jobDao.existsById(id)){
            Job job=jobDao.findById(id).orElseThrow(()->new RuntimeException("Can't find Job Details with Id:"+id));
            //updating fields
            job.setTitle(jobPostDTO.getTitle());
            job.setDescription(jobPostDTO.getDescription());
            job.setVacancy(jobPostDTO.getVacancy());
            job.setLocation(jobPostDTO.getLocation());
            job.setSkillSets(jobPostDTO.getSkillSets());
            return jobDao.save(job);
        }
        throw new RuntimeException("can't find job with id:"+id);
    }

    @Transactional
    public String deleteJobById(String id){
        if(jobDao.existsById(id)){
            jobDao.deleteById(id);
            return "success";
        }
        throw  new RuntimeException("can't find job by id:"+id);
    }

    public List<JobDTO> findJobsByTitle(String title){
        return jobDao.findByTitle(title).stream().map(jobMapper::toDTO).collect(Collectors.toList());
    }

    public List<JobDTO> findJobsByLocation(String location){
        return jobDao.findByLocation(location).stream().map(jobMapper::toDTO).collect(Collectors.toList());
    }
    public List<JobDTO> findJobsBySkillSets(String skils){
        return jobDao.findBySkillSetsContaining(skils).stream().map(jobMapper::toDTO).collect(Collectors.toList());
    }
}
