package com.spring_boot.HRMS.controller.admin;

import com.spring_boot.HRMS.dtos.AdminDTO;
import com.spring_boot.HRMS.dtos.AdminPostDTO;
import com.spring_boot.HRMS.dtos.HrPostDTO;
import com.spring_boot.HRMS.entity.Admin;
import com.spring_boot.HRMS.entity.HR;
import com.spring_boot.HRMS.exceptionHandling.ErrorResponse;
import com.spring_boot.HRMS.exceptionHandling.OwnershipValidationException;
import com.spring_boot.HRMS.service.AdminService;
import com.spring_boot.HRMS.service.HRService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin")
@AllArgsConstructor
@Tag(name = "Admin",description = "Operations related to Admin")
public class AdminController {

    private HRService hrService;
    private AdminService adminService;
    @Operation(
            summary = "Get Admin Dashboard"
    )
    @GetMapping()
    public ResponseEntity<String> getAdminDashBoard(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to HRMS Portal [ADMIN]");
    }

    @Operation(
            summary = "Get Admin Details By Admin Id",
            description = "This endpoint retrieves Admin Details by an Unique Admin Id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Admin Details Found",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = AdminDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "Admin Details Not Found",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/profile/{id}")
    public ResponseEntity<AdminDTO> getAdminProfileById(
            @Parameter(description = "Unique ID of Admin")
            @PathVariable String id) throws Exception {
        long adminId;
        try{
            adminId=Long.parseLong(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(id+" can't be converted into a long value.");
        }

        //Extracting logged-in user details
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userEmail=authentication.getName();

        //Getting Candidate details by Candidate Id
        AdminDTO admin=adminService.getAdminById(adminId);
        //Validating Ownership based authentication
        if(!admin.getEmail().equals(userEmail)){
            throw new OwnershipValidationException("Access Denied! you are not authorized to view this account.");
        }
        //Success
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }


    @Operation(
            summary = "Add an Admin",
            description = "This endpoint post an Admin into DB",
            responses = {
                    @ApiResponse(responseCode = "201",description = "Admin Added"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                    @ApiResponse(responseCode = "400",description = "BAD Request")
            }
    )
    @PostMapping("/add-admin")
    public ResponseEntity<String> addAdmin(@RequestBody AdminPostDTO adminPostDTO){
        try{
            Admin admin=adminService.addAdmin(adminPostDTO);
            if(admin.getId()>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("Admin is added successfully");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD Request");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!"+e.getMessage());
        }
    }


    @Operation(
            summary = "Add a HR",
            description = "This endpoint post a HR into DB",
            responses = {
                    @ApiResponse(responseCode = "201",description = "HR Added"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                    @ApiResponse(responseCode = "400",description = "BAD Request")
            }
    )
    @PostMapping("/add-hr")
    public ResponseEntity<String> addHr(@RequestBody HrPostDTO hrPostDTO){
        try{
            HR newHr=hrService.saveHrProfile(hrPostDTO);
            if(newHr.getId()>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("Hr is added successfully");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD Request.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!"+e.getMessage());
        }

    }


    @Operation(
            summary = "Delete a HR by  ID",
            description = "This endpoint delete a HR by Unique HrID",
            responses = {
                    @ApiResponse(responseCode = "200",description = "HR Deleted Successfully"),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error"),
                    @ApiResponse(responseCode = "404",description = "HR ID Not Found")
            }
    )
    @DeleteMapping("/delete-hr/{id}")
    public ResponseEntity<String> deleteHrProfile(
            @Parameter(description = "Unique ID of HR")
            @PathVariable String id) throws Exception {
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
