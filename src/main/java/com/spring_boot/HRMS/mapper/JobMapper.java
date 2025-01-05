package com.spring_boot.HRMS.mapper;

import com.spring_boot.HRMS.dtos.JobDTO;
import com.spring_boot.HRMS.dtos.JobPostDTO;
import com.spring_boot.HRMS.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(source = "id",target ="jobId" )
    JobDTO toDTO(Job job);

    @Mapping(source = "hrID",target = "hr.id")
    Job toEntity(JobPostDTO jobPostDTO);
}
