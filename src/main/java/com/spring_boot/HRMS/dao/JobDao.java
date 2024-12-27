package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDao extends JpaRepository<Job,String> {

}
