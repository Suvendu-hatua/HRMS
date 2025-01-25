package com.spring_boot.HRMS.service;

import com.spring_boot.HRMS.dao.AdminDao;
import com.spring_boot.HRMS.dtos.AdminDTO;
import com.spring_boot.HRMS.dtos.AdminPostDTO;
import com.spring_boot.HRMS.entity.Admin;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDao adminDao;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;

    public AdminDTO getAdminByEmail(String userEmail){
        Admin admin=adminDao.findByPersonEmail(userEmail).orElseThrow(()->new ProfileNotFoundException("can't find Admin profile with email:"+userEmail));
        //converting admin to AdminDTO
        return adminMapper.toDTO(admin);
    }
    public AdminDTO getAdminById(long id){
        Admin admin= adminDao.findById(id).orElseThrow(()->new ProfileNotFoundException("Admin profile is not found with id:"+id));
        //converting Admin to AdminDTO
        return adminMapper.toDTO(admin);
    }

    @Transactional
    public Admin addAdmin(AdminPostDTO adminPostDTO){
        Admin admin=adminMapper.toEntity(adminPostDTO);
        //setting admin role
        admin.getPerson().setRole("ROLE_ADMIN");
        //encrypting password
        admin.getPerson().setPassword(passwordEncoder.encode(admin.getPerson().getPassword()));
        //saving into DB
        return adminDao.save(admin);
    }
    /**
     * @param id
     * @param adminPostDTO
     * @return
     */
    @Transactional
    public Admin updateAdminProfile(long id, AdminPostDTO adminPostDTO){
        if(adminDao.existsById(id)){
            Admin admin=adminDao.findById(id).orElseThrow(()->new ProfileNotFoundException("Can;t find Admin profile by id:"+id));

            //updating fields.
            admin.setFirstName(adminPostDTO.getFirstName());
            admin.setLastName(adminPostDTO.getLastName());
            admin.setMobileNumber(adminPostDTO.getMobileNumber());

            return adminDao.save(admin);
        }
        throw new RuntimeException("can't update Admin profile.");
    }

}
