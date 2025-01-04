package com.spring_boot.HRMS.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AdminDTO {
    protected long id;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String mobileNumber;


}
