package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.HrDao;
import com.spring_boot.HRMS.entity.HR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HRService {

    private HrDao hrDao;

    @Autowired
    public HRService(HrDao hrDao) {
        this.hrDao = hrDao;
    }

    /**
     * Find Hr by id
     * @param id
     * @return HR
     */

    public HR getHrById(long id){
       return hrDao.findById(id).orElseThrow(()->new RuntimeException("Hr profile is not found with id:"+id));
    }

    /**
     * Save Hr profile
     * @param hr
     * @return
     */
    @Transactional
    public HR saveHrProfile(HR hr){
        return hrDao.save(hr);
    }

    /**
     * Update Hr profile
     * @param id
     * @param updatedHr
     * @return
     */

    @Transactional
    public HR updateHrProfile(long id,HR updatedHr){
        HR hr=getHrById(id);
        if(hr!=null){
            hr.setFirstName(updatedHr.getFirstName());
            hr.setLastName(updatedHr.getLastName());
            hr.setMobileNumber(updatedHr.getMobileNumber());
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
        HR hr=getHrById(id);
        try{
            hrDao.delete(hr);
        }
        catch (Exception e){
            throw new RuntimeException("can't find HR profile to delete by id:"+id);
        }
    }

}
