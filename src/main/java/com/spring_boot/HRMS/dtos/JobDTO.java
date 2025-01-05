package com.spring_boot.HRMS.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class JobDTO {
    private String jobId;
    private String title;
    private String description;
    private String[] skillSets;
    private int vacancy;
    private LocalDate publishedDate;
    private String location;
    private String jobType;
}
