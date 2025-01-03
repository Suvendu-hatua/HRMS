package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateDao extends JpaRepository<Candidate,Long> {
}
