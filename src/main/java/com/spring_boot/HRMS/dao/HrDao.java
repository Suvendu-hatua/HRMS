package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.HR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrDao extends JpaRepository<HR,Long> {

}
