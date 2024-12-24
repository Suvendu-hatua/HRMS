package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.HR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HrDao extends JpaRepository<HR,Long> {

    Optional<HR> findByEmail(String email);
}
