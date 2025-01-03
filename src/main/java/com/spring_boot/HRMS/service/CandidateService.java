package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.CandidateDao;
import com.spring_boot.HRMS.entity.Candidate;
import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CandidateService {

    private CandidateDao candidateDao;
    private JobService jobService;

    /**
     * @param candidate
     * @return
     */
    @Transactional
    public Candidate registerCandidate(Candidate candidate){
        candidate=candidateDao.save(candidate);
        if(candidate.getId()>0){
            log.info("candidate is registered successfully.");
            return candidate;
        }else{
            throw new RuntimeException("can't create candidate.");
        }
    }

    public Candidate getCandidateById(long id){
        return candidateDao.findById(id).orElseThrow(()->new RuntimeException("can't find candidate with id:"+id));
    }

    /**
     * @param id
     * @param candidate
     * @return
     */
    @Transactional
    public Candidate updateCandidate(long id,Candidate candidate){
        if(candidateDao.existsById(id)){
            candidate.setId(id);
            return candidateDao.save(candidate);
        }else{
            throw new RuntimeException("can't find candidate profile to update with id:"+id);
        }
    }

    /**
     * @param id
     */
    @Transactional
    public void deleteCandidateById(long id){
        Candidate candidate=getCandidateById(id);
        try{
            candidateDao.delete(candidate);
        }catch (Exception e){
            throw new RuntimeException("can't find candidate to delete with id:"+id);
        }
    }

    @Transactional
    public String applyToJob(String email,String jobId){
        try{
            //Fetch candidate details from logged-in user's email
            Candidate candidate=candidateDao.findByPersonEmail(email).orElseThrow(()->new ProfileNotFoundException("can't find person with username"));
            //Fetch job details from job table using jobId
            Job job=jobService.getJobById(jobId);
            //Adding Job to candidate
            candidate.getJobs().add(job);
            //Saving updated candidate in the DB.
            candidateDao.save(candidate);
            log.info("Successfully applied to the Job");
            return "Success";
        }catch (Exception e){
            throw new RuntimeException("can't apply to the Job due to "+e.getMessage());
        }
    }
}
