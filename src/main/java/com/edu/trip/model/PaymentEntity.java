package com.edu.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    private Date paymentDate;

    private String paymentMethod;

    private long paymentAmount;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private InvoiceEntity invoice;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private BookingEntity booking;
}
