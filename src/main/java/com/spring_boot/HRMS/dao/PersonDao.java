package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonDao extends JpaRepository<Person,Long> {
    Optional<Person> findByEmail(String email);
}
