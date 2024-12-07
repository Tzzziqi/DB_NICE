package com.edu.trip.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;

    private String name;

    @Column(length = 1024)
    private String description;

    private int status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "invoice")
    private PaymentEntity payment;

    @Column(nullable = false)
    private long money;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "booking_id")
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private BookingEntity booking;

}
