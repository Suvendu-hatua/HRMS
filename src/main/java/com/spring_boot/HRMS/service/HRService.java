package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.CandidateDao;
import com.spring_boot.HRMS.dao.HrDao;
import com.spring_boot.HRMS.dao.JobDao;
import com.spring_boot.HRMS.dtos.CandidateDTO;
import com.spring_boot.HRMS.dtos.HrDTO;
import com.spring_boot.HRMS.dtos.HrPostDTO;
import com.spring_boot.HRMS.dtos.JobDTO;
import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.mapper.CandidateMapper;
import com.spring_boot.HRMS.mapper.HrMapper;
import com.spring_boot.HRMS.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HRService {

    private final HrDao hrDao;
    private final PasswordEncoder passwordEncoder;
    private  final HrMapper hrMapper;
    private final JobDao jobDao;
    private  final JobMapper jobMapper;
    private final CandidateDao candidateDao;
    private final CandidateMapper candidateMapper;


    /**
     * Find Hr by id
     * @param id
     * @return HR
     */

    public HrDTO getHrById(long id){
       HR hr= hrDao.findById(id).orElseThrow(()->new ProfileNotFoundException("Hr profile is not found with id:"+id));
       //Converting HR to HrDTO
        return hrMapper.toDTO(hr);

    }

    /**
     * Save Hr profile
     * @param hrPostDTO
     * @return
     */
    @Transactional
    public HR saveHrProfile(HrPostDTO hrPostDTO){
        HR hr=hrMapper.toEntity(hrPostDTO);
        //Setting Role
        hr.getPerson().setRole("ROLE_HR");
        //encrypting password
        hr.getPerson().setPassword(passwordEncoder.encode(hr.getPerson().getPassword()));
        //saving into DB
        return hrDao.save(hr);
    }

    /**
     * Update Hr profile
     * @param id
     * @param hrPostDTO
     * @return
     */

    @Transactional
    public HR updateHrProfile(long id,HrPostDTO hrPostDTO){
        if(hrDao.existsById(id)){
            HR hr=hrDao.findById(id).orElseThrow(()->new ProfileNotFoundException("can't find HR profile with id:"+id));
            //updating fields
            hr.setFirstName(hrPostDTO.getFirstName());
            hr.setLastName(hrPostDTO.getLastName());
            hr.setMobileNumber(hrPostDTO.getMobileNumber());
            return hrDao.save(hr);
        }
        throw new RuntimeException("can't update hr profile.");
    }

    /**
     * Delete Hr profile
     * @param id
     */

    @Transactional
    public void deleteHrProfileById(long id){
        try{
            if(hrDao.existsById(id)){
                hrDao.deleteById(id);
            }
        }
        catch (Exception e){
            throw new RuntimeException("can't find HR profile to delete by id:"+id);
        }
    }
    public List<JobDTO> findAllJobPosts(String email){
        //Getting details of HR by email
        HR hr=hrDao.findByPersonEmail(email).orElseThrow(()->new ProfileNotFoundException("can't find HR by email id:"+email));
        //getting all the JobList
        return jobDao.findAllJobsByHrId(hr.getId()).stream().map(jobMapper::toDTO).collect(Collectors.toList());
    }

    public List<CandidateDTO> findAllJobAppliedCandidates(String jobId) {
        return candidateDao.findCandidatesByJobId(jobId).stream().map(candidateMapper::toDTO).collect(Collectors.toList());
    }
}
