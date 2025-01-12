package com.spring_boot.HRMS.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CandidatePostDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;
}
