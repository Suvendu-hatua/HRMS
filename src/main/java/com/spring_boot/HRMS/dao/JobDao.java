package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobDao extends JpaRepository<Job,String> {
        @Query("SELECT j FROM Job j WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))")
        List<Job> findByTitle(@Param("title") String title);

        @Query("SELECT  j FROM Job j WHERE LOWER(j.location) like LOWER(CONCAT('%', :location, '%'))")
        List<Job> findByLocation(@Param("location") String location);
}
