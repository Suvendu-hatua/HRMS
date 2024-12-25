package com.spring_boot.HRMS.controller.admin;

import com.spring_boot.HRMS.entity.Admin;
import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.AdminService;
import com.spring_boot.HRMS.service.HRService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private HRService hrService;
    private AdminService adminService;
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public ResponseEntity<String> getAdminDashBoard(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to HRMS Portal [ADMIN]");
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Admin> getAdminProfileById(@PathVariable String id) throws Exception {
        long adminId;
        try{
            adminId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try{
            return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminById(adminId));
        }catch (Exception e){
            throw new ProfileNotFoundException("can't find Admin profile with id:"+id);
        }
    }

    @PostMapping("/add-hr")
    public ResponseEntity<String> addHr(@RequestBody HR hr){
        try{
            hr.getPerson().setRole("ROLE_HR");
            hr.getPerson().setPassword(passwordEncoder.encode(hr.getPerson().getPassword()));
            HR newHr=hrService.saveHrProfile(hr);

            if(newHr.getId()>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("Hr is added successfully");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!"+e.getMessage());
        }

    }

    @DeleteMapping("/delete-hr/{id}")
    public ResponseEntity<String> deleteHrProfile(@PathVariable String id) throws Exception {
        long hrId;
        try{
            hrId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try{
            hrService.deleteHrProfileById(hrId);
            return ResponseEntity.status(HttpStatus.OK).body("Hr profile deleted successfully.");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
