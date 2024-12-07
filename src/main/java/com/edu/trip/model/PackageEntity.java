package com.edu.trip.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "package")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    private String name;

    private int price;

    /**
     * charge by night / trip
     */
    private String chargeType;

    @Column(length = 1024)
    private String description;

    private String status;

}
