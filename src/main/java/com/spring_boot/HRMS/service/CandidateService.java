package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.CandidateDao;
import com.spring_boot.HRMS.dao.JobDao;
import com.spring_boot.HRMS.dtos.CandidateDTO;
import com.spring_boot.HRMS.dtos.CandidatePostDTO;
import com.spring_boot.HRMS.entity.Candidate;
import com.spring_boot.HRMS.entity.Job;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.mapper.CandidateMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateService {

    private final CandidateDao candidateDao;
    private final JobDao jobDao;
    private final CandidateMapper candidateMapper;
    private final PasswordEncoder passwordEncoder;

    public CandidateDTO getCandidateByEmail(String userEmail){
        Candidate candidate=candidateDao.findByPersonEmail(userEmail).orElseThrow(()->new ProfileNotFoundException("can't find Candidate profile with email:"+userEmail));
        //converting candidate to CandidateDTO
        return candidateMapper.toDTO(candidate);
    }

    /**
     * @param candidatePostDTO
     * @return
     */
    @Transactional
    public Candidate registerCandidate(CandidatePostDTO candidatePostDTO){
       Candidate candidate=candidateMapper.toEntity(candidatePostDTO);
       //setting Role
        candidate.getPerson().setRole("ROLE_CANDIDATE");
        //encrypting password
        candidate.getPerson().setPassword(passwordEncoder.encode(candidate.getPerson().getPassword()));
        //Saving to DB
        candidate=candidateDao.save(candidate);
        if(candidate.getId()>0){
            log.info("candidate is registered successfully.");
            return candidate;
        }else{
            throw new RuntimeException("can't create candidate.");
        }
    }

    public CandidateDTO getCandidateById(long id){
        Candidate candidate= candidateDao.findById(id).orElseThrow(()->new ProfileNotFoundException("can't find candidate with id:"+id));
        //converting to CandidateDTO
        return candidateMapper.toDTO(candidate);
    }

    /**
     * @param  email
     * @param candidatePostDTO
     * @return
     */
    @Transactional
    public Candidate updateCandidate(String email, CandidatePostDTO candidatePostDTO){
            try{
                Candidate candidate=candidateDao.findByPersonEmail(email).orElseThrow(()->new ProfileNotFoundException("can't find Candidate Profile with email:"+email));
                //Updating fields
                candidate.setFirstName(candidatePostDTO.getFirstName());
                candidate.setLastName(candidatePostDTO.getLastName());
                candidate.setMobileNumber(candidate.getMobileNumber());
                return candidateDao.save(candidate);
            }catch (Exception e){
                throw new RuntimeException("can't update candidate profile"+e.getMessage());
            }
    }

    /**
     * @param email
     */
    @Transactional
    public void deleteCandidate(String email){
        try{
            Candidate candidate=candidateDao.findByPersonEmail(email).orElseThrow(()->new ProfileNotFoundException("can't find Candidate Profile with email:"+email));
            candidateDao.deleteById(candidate.getId());
        }catch (Exception e){
            throw new RuntimeException("can't  delete candidate account "+e.getMessage());
        }
    }

    @Transactional
    public String applyToJob(String email,String jobId){
        try{
            //Fetch candidate details from logged-in user's email
            Candidate candidate=candidateDao.findByPersonEmail(email).orElseThrow(()->new ProfileNotFoundException("can't find person with username"));
            //Fetch job details from job table using jobId
            Job job=jobDao.findById(jobId).orElseThrow(()->new RuntimeException("Can't find Job details by Id:"+jobId));
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
