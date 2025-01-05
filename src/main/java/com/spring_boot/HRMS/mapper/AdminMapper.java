package com.spring_boot.HRMS.mapper;

import com.spring_boot.HRMS.dtos.AdminDTO;
import com.spring_boot.HRMS.dtos.AdminPostDTO;
import com.spring_boot.HRMS.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(source = "person.email",target = "email")
    AdminDTO toDTO(Admin admin);

    @Mapping(source = "email",target = "person.email")
    @Mapping(source = "password",target = "person.password")
    Admin toEntity(AdminPostDTO adminPostDTO);
}
