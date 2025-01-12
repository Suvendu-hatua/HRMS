package com.spring_boot.HRMS.dao;

import com.spring_boot.HRMS.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateDao extends JpaRepository<Candidate,Long> {

    @Query("SELECT c from Candidate c where c.person.email = :email")
    Optional<Candidate> findByPersonEmail(@Param("email") String email);

    @Query("SELECT c from Candidate c JOIN FETCH c.jobs  j WHERE  j.id=:jobId")
    List<Candidate> findCandidatesByJobId(@Param("jobId") String jobId);
}
