package com.edu.trip.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "entertainment")
public class EntertainmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entertainmentId;

    private String name;

    private int floor;

    private String entertainmentType;

    @Column(length = 1024)
    private String description;

    private String status;

}
