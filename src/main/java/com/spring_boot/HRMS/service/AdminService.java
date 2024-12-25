package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.AdminDao;
import com.spring_boot.HRMS.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private AdminDao adminDao;

    /**
     * @param adminDao
     */
    @Autowired
    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public Admin getAdminById(long id){
        return adminDao.findById(id).orElseThrow(()->new RuntimeException("Admin profile is not found with id:"+id));
    }

    /**
     * @param id
     * @param updatedAdmin
     * @return
     */
    @Transactional
    public Admin updateAdminProfile(long id, Admin updatedAdmin){
        if(adminDao.existsById(id)){
            updatedAdmin.setId(id);
            return adminDao.save(updatedAdmin);
        }
        throw new RuntimeException("can't update Admin profile.");
    }

}
