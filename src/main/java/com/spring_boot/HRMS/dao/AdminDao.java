package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin,Long> {
    @Query("SELECT a FROM Admin a WHERE a.person.email = :email")
    Optional<Admin> findByPersonEmail(@Param("email") String email);
}
