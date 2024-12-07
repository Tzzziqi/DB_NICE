package com.edu.trip.controller.view;

import com.edu.trip.data_view.ListTripItemView;
import com.edu.trip.data_view.TripDetailsView;
import com.edu.trip.jpa.BookingRepository;
import com.edu.trip.jpa.PortRepository;
import com.edu.trip.jpa.TripRepository;
import com.edu.trip.model.BookingEntity;
import com.edu.trip.model.TripEntity;
import com.edu.trip.po.TripStatus;
import com.edu.trip.po.UserRole;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/view/trip")
@RestController
public class TripViewController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PortRepository portRepository;

    @RequestMapping("/read")
    public ResponseEntity read(
            @RequestAttribute(value = "userId", required = false) Long userId,
            @RequestAttribute(value = "role", required = false) UserRole role,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        // by default, view for registering
        if (StringUtils.isBlank(status)) {
            status = TripStatus.REGISTERING.name();
        }
        TripStatus statusEnum;
        try {
            statusEnum = TripStatus.valueOf(status);
        } catch (Throwable e) {
            return ResponseUtil.fail("Invalid status parameter.");
        }
        List<TripEntity> trips = StringUtils.isBlank(keyword) ?
                tripRepository.findAllByStatusIsOrderByStartDateDesc(statusEnum.name()) :
                tripRepository.findAllByNameContainingAndStatusIsOrderByStartDateDesc(keyword, statusEnum.name());
        if (userId == null || role != UserRole.CUSTOMER) {
            // just is visitor
            return ResponseUtil.ok(new ListTripItemView(trips, new ArrayList<>()));
        }
        // this should also check the user id status
        List<Long> joined = tripRepository.findJoinedTripByUserId(userId);
        return ResponseEntity.ok(new ListTripItemView(trips, joined));
    }

    @GetMapping("/readSingle")
    public ResponseEntity readSingle(
            @RequestAttribute(value = "userId", required = false) Long userId,
            @RequestAttribute(value = "role", required = false) UserRole role,
            @RequestParam(value = "tripId") long tripId
    ) {
        Optional<TripEntity> tripDataOpt = tripRepository.findById(tripId);
        if (tripDataOpt.isEmpty()) {
            return ResponseUtil.fail("Invalid trip id.");
        }
        TripEntity tripData = tripDataOpt.get();
        List<BookingEntity> allBookings = bookingRepository.findAllByTrip(tripId);
        List<Long> bookedRoom = new ArrayList<>();
        allBookings.forEach(x -> x.getStaterooms().forEach(r -> bookedRoom.add(r.getStateroomId())));
        if (userId == null || role != UserRole.CUSTOMER) {
            return ResponseUtil.ok(new TripDetailsView(tripData, new ArrayList<>(), bookedRoom));
        }
        // find all trip of mine associated to it
        List<BookingEntity> booking = new ArrayList<>();
        for (BookingEntity b : allBookings) {
            if (Objects.equals(b.getUser().getId(), userId)) {
                booking.add(b);
            }
        }

        return ResponseUtil.ok(new TripDetailsView(tripData, booking, bookedRoom));
    }

    @GetMapping("/searchPort")
    public ResponseEntity searchPort(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "keyword", required = false) String keyword) {
        boolean findAll = StringUtils.isBlank(keyword);
        keyword = keyword == null ? null : keyword.strip();
        if (findAll) {
            return null;
        }
        return null;
    }

}
