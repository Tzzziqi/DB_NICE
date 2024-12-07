package com.edu.trip.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "booking_group")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", orphanRemoval = true)
    private List<PassengerEntity> passengers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "booking_id")
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private BookingEntity booking;

}
