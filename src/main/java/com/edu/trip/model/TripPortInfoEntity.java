package com.edu.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Entity
@Table(name = "trip_port_info")
public class TripPortInfoEntity {

    @EmbeddedId
    private TripPortInfoId id;

    private Date arrivalDate;

    private Date departureDate;

    @MapsId("tripId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="trip_id")
    @JsonIgnore
    @ToString.Exclude
    private TripEntity trip;

    @MapsId("portId")
    @ManyToOne
    @JoinColumn(name = "port_id")
    private PortEntity port;

}
