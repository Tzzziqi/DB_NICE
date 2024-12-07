package com.edu.trip.data_view;

import com.edu.trip.model.TripEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTripItemView {

    private List<TripEntity> trips;

    private List<Long> joinedTrips;

}
