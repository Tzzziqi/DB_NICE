package com.edu.trip.data_view;

import com.edu.trip.model.InvoiceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceView {

    private InvoiceEntity invoice;

    private long bookingId;

    private long tripId;

    private String tripName;

}
