package com.spring_boot.HRMS.mapper;

import com.spring_boot.HRMS.dtos.CandidateDTO;
import com.spring_boot.HRMS.dtos.CandidatePostDTO;
import com.spring_boot.HRMS.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    @Mapping(source = "person.email",target = "email")
    CandidateDTO toDTO(Candidate candidate);

    @Mapping(source = "email",target = "person.email")
    @Mapping(source = "password",target = "person.password")
    Candidate toEntity(CandidatePostDTO candidatePostDTO);
}
