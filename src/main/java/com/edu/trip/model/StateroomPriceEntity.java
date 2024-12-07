package com.edu.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "state_room_price")
public class StateroomPriceEntity {

    @EmbeddedId
    private StateroomPriceId id;

    private int priceNight;

    @MapsId("tripId")
    @ManyToOne
    @JoinColumn(name="trip_id")
    @JsonIgnore
    @ToString.Exclude
    private TripEntity trip;

    @MapsId("stateroomId")
    @ManyToOne
    @JoinColumn(name = "stateroom_id")
    private StateroomEntity stateroom;

}
