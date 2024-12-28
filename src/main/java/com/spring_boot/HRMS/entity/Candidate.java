package com.spring_boot.HRMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "candidate")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String mobileNumber;

    //Adding one-to-one relationship with Person Entity
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    //Adding Many-to-many relationship with Job Entity (one candidate can have multiple jobs applied and similarly vice versa)
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinTable(
            name = "candidate_job",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<Job> jobs;

}
