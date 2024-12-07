package com.edu.trip.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tripId;

    private String name;

    private int tripNights;

    private Date startDate;

    private Date endDate;

    /**
     * mark the status of this trip.
     * registering / started (can buy package) / finished (view only)
     */
    private String status;

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StateroomPriceEntity> staterooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "start_port_id")
    private PortEntity startPort;

    @ManyToOne
    @JoinColumn(name = "end_port_id")
    private PortEntity endPort;

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripPortInfoEntity> tripPorts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "trip_restaurant_relation",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "trip_entertainment_relation",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "entertainment_id")
    )
    private List<EntertainmentEntity> entertainments = new ArrayList<>();

}
