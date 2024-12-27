package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.JobDao;
import com.spring_boot.HRMS.entity.Job;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class JobService {
    private JobDao jobDao;

    public List<Job> getAllJobs(){
        return jobDao.findAll();
    }

    public Job getJobById(String id){
        return jobDao.findById(id).orElseThrow(()->new RuntimeException("can't find Job by id:"+id));
    }

    @Transactional
    public Job saveJob(Job job){
        //generating random ID
        job.setId(UUID.randomUUID().toString());
        return jobDao.save(job);
    }
    @Transactional
    public Job updateJob(String id,Job job){
        if(jobDao.existsById(id)){
            job.setId(id);
            return jobDao.save(job);
        }
        throw new RuntimeException("can't find job id:"+id);
    }

    @Transactional
    public String deleteJobById(String id){
        Job job =getJobById(id);
        if(job!=null){
            jobDao.delete(job);
            return "success";
        }
        throw  new RuntimeException("can't find job by id:"+id);
    }
}
