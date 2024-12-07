package com.edu.trip.jpa;

import com.edu.trip.model.EntertainmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface EntertainmentRepository extends ResourceRepository<EntertainmentEntity> {

    EntertainmentEntity findByEntertainmentIdAndStatus(Long id, String status);


    EntertainmentEntity findByNameAndFloorAndStatus(String name, int floor, String status);

}
