package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.HR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HrDao extends JpaRepository<HR,Long> {
    @Query("SELECT h FROM HR h WHERE h.person.email = :email")
        Optional<HR> findByPersonEmail(@Param("email") String email);
}
