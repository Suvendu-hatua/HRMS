package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin,Long> {

}
