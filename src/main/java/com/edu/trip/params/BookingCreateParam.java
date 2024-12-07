package com.edu.trip.params;

import com.edu.trip.model.PassengerEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingCreateParam {

    private Long bookingId;

    private Long tripId;

    private List<PassengerEntity> passengers = new ArrayList<>();

    private List<Long> staterooms = new ArrayList<>();

    private List<Long> packages = new ArrayList<>();

}
