package com.edu.trip.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "port")
public class PortEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portId;

    private String name;

    private String country;

    private String state;

    private String address;

    @Column(length = 1024)
    private String nearestAirport;

    private int parkingSpots;

    private String status;

}
