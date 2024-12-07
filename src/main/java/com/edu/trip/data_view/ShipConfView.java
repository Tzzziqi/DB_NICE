package com.edu.trip.data_view;

import com.edu.trip.model.EntertainmentEntity;
import com.edu.trip.model.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipConfView {

    private List<RestaurantEntity> restaurants;

    private List<EntertainmentEntity> entertainments;

}
