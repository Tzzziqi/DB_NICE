package com.edu.trip.jpa;

import com.edu.trip.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("select book from BookingEntity book where book.user.id = :userId and book.trip.tripId = :tripId")
    List<BookingEntity> findAllByTrip(@Param("userId") long userId, @Param("tripId") long tripId);

    @Query("select book from BookingEntity book where book.trip.tripId = :tripId")
    List<BookingEntity> findAllByTrip(@Param("tripId") long tripId);

    @Query("select book from BookingEntity book left join fetch book.trip where book.user.id = :userId and book.trip.name like %:name%")
    List<BookingEntity> findAllTripByUser(@Param("userId") long userId, @Param("name") String name);

    @Query("select book from BookingEntity book left join fetch book.trip where book.user.id = :userId")
    List<BookingEntity> findAllByUserId(long userId);

    @Query("select book from BookingEntity book left join fetch book.trip where book.bookingId = :bookingId")
    Optional<BookingEntity> findBookAndTripById(@Param("bookingId") long tripId);
}
