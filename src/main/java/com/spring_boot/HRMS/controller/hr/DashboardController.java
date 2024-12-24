package com.spring_boot.HRMS.controller.hr;

import com.spring_boot.HRMS.service.HRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private HRService hrService;

    @Autowired
    public DashboardController(HRService hrService) {
        this.hrService = hrService;
    }

    @GetMapping()
    public String getDashboard(){
        return "Welcome to HRMS Application";
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id){
        try {
            long hrId=Long.parseLong(id);
            return ResponseEntity.ok(hrService.getHrById(hrId));

        }catch (Exception e){

        }
        return null;
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateHrProfile(@PathVariable String id){
        return null;
    }
}
