package com.spring_boot.HRMS.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hr")
public class HR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_no",length = 10)
    private String mobileNumber;

    @OneToOne
    @JoinColumn(name = "person_id",nullable = false)
    private Person person;

    //Adding Constructor

    public HR() {
    }

    public HR(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

//Adding Setter and Getter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    //Overriding toString()


    @Override
    public String toString() {
        return "HR{" +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                '}';
    }
}
