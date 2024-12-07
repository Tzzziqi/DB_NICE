package com.edu.trip.jpa;

import com.edu.trip.model.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {

    List<TripEntity> findAllByStatusIsOrderByStartDateDesc(String status);

    List<TripEntity> findAllByNameContainingAndStatusIsOrderByStartDateDesc(String name, String status);


    @Query("select distinct book.trip.tripId from BookingEntity book where book.user.id = :userId")
    List<Long> findJoinedTripByUserId(@Param("userId") long userId);

}
