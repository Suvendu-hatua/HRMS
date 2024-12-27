package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobDao extends JpaRepository<Job,String> {
        List<Job> findByTitle(String title);
        List<Job> findByLocation(String location);
}
