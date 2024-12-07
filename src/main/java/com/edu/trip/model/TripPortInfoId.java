package com.edu.trip.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Embeddable
public class TripPortInfoId implements Serializable {

    @Column
    private long tripId;
    @Column
    private long portId;

}
