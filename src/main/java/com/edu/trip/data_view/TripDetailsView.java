package com.edu.trip.data_view;

import com.edu.trip.model.BookingEntity;
import com.edu.trip.model.TripEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDetailsView {

    private TripEntity trip;

    private List<BookingEntity> booking;

    private List<Long> bookedRooms = new ArrayList<>();

}
