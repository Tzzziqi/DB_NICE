package com.edu.trip.data_view;

import com.edu.trip.model.BookingEntity;
import com.edu.trip.model.TripEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingView {

    private BookingEntity booking;

    private TripEntity trip;

    private List<Long> bookedRooms = new ArrayList<>();

}
