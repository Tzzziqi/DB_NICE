package com.edu.trip.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "location")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locationId;

    private String location;


}
