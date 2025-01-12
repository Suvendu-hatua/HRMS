package com.spring_boot.HRMS.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JobPostDTO {
    private String title;
    private String description;
    private String skillSets;
    private int vacancy;
    private String location;
    private String jobType;
}
