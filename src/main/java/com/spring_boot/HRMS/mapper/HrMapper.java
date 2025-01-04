package com.spring_boot.HRMS.mapper;

import com.spring_boot.HRMS.dtos.HrDTO;
import com.spring_boot.HRMS.dtos.HrPostDTO;
import com.spring_boot.HRMS.entity.HR;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HrMapper {
    @Mapping(source = "person.email",target = "email")
    HrDTO toDTO(HR hr);

    @Mapping(source = "password",target = "person.password")
    @Mapping(source = "email",target = "person.email")
    HR toEntity(HrPostDTO hrPostDTO);
}
