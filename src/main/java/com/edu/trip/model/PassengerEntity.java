package com.edu.trip.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@Entity
@Table(name = "passenger")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long passengerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false)
    private String country;

    private String state;

    private String city;

    private String street;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private GroupEntity group;


}
