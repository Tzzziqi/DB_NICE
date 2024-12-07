package com.edu.trip.jpa;

import com.edu.trip.model.RestaurantEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ResourceRepository<RestaurantEntity> {


    RestaurantEntity findByRestaurantIdAndStatus(Long id, String status);

    RestaurantEntity findByNameAndFloorAndStatus(String name, int floor, String status);

}
