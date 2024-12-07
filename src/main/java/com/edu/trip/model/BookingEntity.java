package com.edu.trip.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;


    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private TripEntity trip;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL, mappedBy = "booking")
    private GroupEntity group;

    /**
     * @note: only detach here, managed by trip
     */
    @ManyToMany
    @JoinTable(
            name = "booking_room_relation",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = {@JoinColumn(name = "stateroom_id")}
    )
    private List<StateroomEntity> staterooms = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "booking_package_relation",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = {@JoinColumn(name = "package_id")}
    )
    private List<PackageEntity> packages = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<InvoiceEntity> invoices = new ArrayList<>();

    private String status;

}
