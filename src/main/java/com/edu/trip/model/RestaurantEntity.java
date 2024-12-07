package com.edu.trip.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    private String name;

    private String servingTime;

    private int floor;

    private String restaurantType;

    @Column(length = 1024)
    private String description;

    private String status;

}
