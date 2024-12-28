package com.spring_boot.HRMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "job")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @Column
    private String id;

    @Column(nullable = false)
    private String title;
    private String description;
    private String[] skillSets;
    private int vacancy;
    private LocalDate publishedDate;
    private String location;
    private String jobType;

    //Adding many-to-one relationship with Job Entity
    @ManyToOne( cascade = {CascadeType.DETACH,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "hr_id",referencedColumnName = "id")
    private HR hr;

    //Adding Many-to-Many relationship with Candidate Entity
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE})
    @JoinTable(
            name = "candidate_job",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private List<Candidate> candidates;

}
