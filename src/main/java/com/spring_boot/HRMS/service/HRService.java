package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.HrDao;
import com.spring_boot.HRMS.dtos.HrDTO;
import com.spring_boot.HRMS.dtos.HrPostDTO;
import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.mapper.HrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HRService {

    private final HrDao hrDao;
    private final PasswordEncoder passwordEncoder;
    private  final HrMapper hrMapper;


    /**
     * Find Hr by id
     * @param id
     * @return HR
     */

    public HrDTO getHrById(long id){
       HR hr= hrDao.findById(id).orElseThrow(()->new RuntimeException("Hr profile is not found with id:"+id));
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

}
