package com.edu.trip.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class StateroomPriceId implements Serializable {
    @Column
    private long tripId;
    @Column
    private long stateroomId;
}