package com.edu.trip.jpa;

import com.edu.trip.model.BookingEntity;
import com.edu.trip.model.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    int countAllByBookingAndPaymentIsNull(BookingEntity booking);

    @Query("select iv from InvoiceEntity iv left join fetch iv.booking left join fetch iv.booking.trip where iv.booking.user.id = :userId and iv.payment is not null order by iv.invoiceId desc ")
    List<InvoiceEntity> findAllByUserIdPaid(@Param("userId") long userId);

    @Query("select iv from InvoiceEntity iv left join fetch iv.booking left join fetch iv.booking.trip where iv.booking.user.id = :userId and iv.payment is  null order by iv.invoiceId desc ")
    List<InvoiceEntity> findAllByUserIdNoPayment(@Param("userId") long userId);

}
