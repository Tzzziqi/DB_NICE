package com.edu.trip.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

@Data
@Entity
@Table(name = "stateroom")
public class StateroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stateroomId;

    private int roomSize;

    private int numberBeds;

    private float numberBath;

    private int balconyCount;

    private String stateroomType;

    private String status;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    private LocationEntity location;

}
