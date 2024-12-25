package com.spring_boot.HRMS.controller.hr;

import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.exceptionHandling.ProfileNotFoundException;
import com.spring_boot.HRMS.service.HRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
@Slf4j
public class HrController {

    private HRService hrService;

    @Autowired
    public HrController(HRService hrService) {
        this.hrService = hrService;
    }

    @GetMapping()
    public String getDashboard(){
        return "Welcome to HRMS Application";
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<HR> getProfile(@PathVariable String id) throws Exception {
        long hrId;
        try{
            hrId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try {
            return ResponseEntity.ok(hrService.getHrById(hrId));
        }catch (Exception e){
            throw new ProfileNotFoundException("can't find Hr profile with id:"+id);
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<HR> updateHrProfile(@PathVariable String id, @RequestBody HR hr) throws Exception {
        long hrId;
        try{
            hrId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        try{
            return ResponseEntity.ok(hrService.updateHrProfile(hrId,hr));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }

    }
}
