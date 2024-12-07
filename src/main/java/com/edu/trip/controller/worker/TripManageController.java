package com.edu.trip.controller.worker;

import com.edu.trip.data_view.TripDetailsView;
import com.edu.trip.jpa.*;
import com.edu.trip.model.*;
import com.edu.trip.params.ChangeTripStatusParam;
import com.edu.trip.params.TripCreateParam;
import com.edu.trip.po.TripStatus;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RequestMapping("/employee/trip")
@RestController
public class TripManageController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private StateroomRepository stateroomRepository;

    @Autowired
    private EntertainmentRepository entertainmentRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private BookingRepository bookingRepository;


    @GetMapping("/read")
    public ResponseEntity read(@RequestParam("status") String status, @RequestParam(value = "keyword", required = false) String keyword) {
        status = StringUtils.isBlank(status) ? TripStatus.REGISTERING.name() : status;
        return ResponseEntity.ok(
                StringUtils.isBlank(keyword) ?
                        tripRepository.findAllByStatusIsOrderByStartDateDesc(status) :
                        tripRepository.findAllByNameContainingAndStatusIsOrderByStartDateDesc(keyword.strip(), status)
        );
    }

    @GetMapping("/readSingle")
    public ResponseEntity read(@RequestParam("tripId") Long tripId) {
        if (tripId == null) {
            return ResponseUtil.fail("Trip not exists");
        }
        Optional<TripEntity> tripEntityOp = tripRepository.findById(tripId);
        if (tripEntityOp.isEmpty()) {
            return ResponseUtil.fail("Trip not exists");
        }
        TripEntity tripEntity = tripEntityOp.get();
        List<BookingEntity> bookings = bookingRepository.findAllByTrip(tripId);
        return ResponseUtil.ok(new TripDetailsView(tripEntity, bookings, new ArrayList<>()));
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam long tripId) {
        Optional<TripEntity> tripEntityOp = tripRepository.findById(tripId);
        if (tripEntityOp.isEmpty()) {
            return ResponseUtil.fail("Trip not exists");
        }
        TripEntity tripEntity = tripEntityOp.get();
        if (!TripStatus.REGISTERING.name().equals(tripEntity.getStatus())) {
            return ResponseUtil.fail("Invalid trip status");
        }
        tripRepository.delete(tripEntity);
        return ResponseUtil.ok(null);
    }


    @PostMapping("/updateStatus")
    public ResponseEntity updateStatus(@RequestBody ChangeTripStatusParam tripCreate) {
        if (tripCreate.getTripId() == null) {
            return ResponseUtil.fail("Trip not exists");
        }
        TripStatus newStatus;
        try {
            newStatus = TripStatus.valueOf(tripCreate.getStatus());
            if (newStatus == TripStatus.REGISTERING) {
                return ResponseUtil.fail("Invalid trip status");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return ResponseUtil.fail("Invalid trip status");
        }

        Optional<TripEntity> tripEntityOp = tripRepository.findById(tripCreate.getTripId());
        if (tripEntityOp.isEmpty()) {
            return ResponseUtil.fail("Trip not exists");
        }
        TripEntity tripEntity = tripEntityOp.get();
        if (newStatus == TripStatus.STARTED && !TripStatus.REGISTERING.name().equals(tripEntity.getStatus())) {
            return ResponseUtil.fail("Invalid trip status");
        } else if (newStatus == TripStatus.FINISHED && !TripStatus.STARTED.name().equals(tripEntity.getStatus())) {
            return ResponseUtil.fail("Invalid trip status");
        }
        tripEntity.setStatus(newStatus.name());
        tripRepository.saveAndFlush(tripEntity);

        return ResponseUtil.ok(null);
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TripCreateParam tripCreate) {
        if (StringUtils.isBlank(tripCreate.getTripName())) {
            return ResponseUtil.fail("Invalid Empty Trip Name");
        }

        if (tripCreate.getStartDate() == null ||
                tripCreate.getEndDate() == null ||
                tripCreate.getEndDate().before(tripCreate.getStartDate())) {
            return ResponseUtil.fail("Invalid trip start / end date");
        }

        int tripNights = 0;
        if (tripCreate.getEndDate().getTime() - tripCreate.getStartDate().getTime() < 24 * 60 * 60 * 1000L) {
            // must be at most night
            tripNights = tripCreate.getEndDate().getDay() == tripCreate.getStartDate().getDay() ? 0 : 1;
        } else {
            Calendar start = Calendar.getInstance();
            start.setTimeInMillis(1000L * (long)(tripCreate.getStartDate().getTime() / 1000));
            start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE), 0, 0, 0);

            Calendar end = Calendar.getInstance();
            end.setTimeInMillis(1000L * (long)(tripCreate.getEndDate().getTime() / 1000));
            end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DATE), 0, 0, 0);

            LocalDateTime date1 = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime date2 = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            tripNights = (int) Duration.between(date1, date2).toDays();
        }


        List<TripCreateParam.TripPortView> portsView = tripCreate.getPorts();
        Date lastDate = tripCreate.getStartDate();
        Date endDate = tripCreate.getEndDate();
        long lastId = tripCreate.getStartPortId();

        Set<Long> toFetchPortIds = new HashSet<>();
        toFetchPortIds.add(tripCreate.getStartPortId());
        toFetchPortIds.add(tripCreate.getEndPortId());
        for (int i = 0; i < portsView.size(); ++i) {
            TripCreateParam.TripPortView step = portsView.get(i);
            if (step.getPortId() == lastId) {
                return ResponseUtil.fail("Two identical adjacent ports in trip is invalid.");
            }
            if (!step.getArrivalDate().after(lastDate) || !step.getArrivalDate().before(step.getDepartureDate()) || !step.getDepartureDate().before(endDate)) {
                return ResponseUtil.fail("Illegal arrival or departure date. At the No." + i + " Port");
            }
            lastId = step.getPortId();
            toFetchPortIds.add(step.getPortId());
        }
        if (portsView.size() > 0 && tripCreate.getEndPortId() == lastId) {
            return ResponseUtil.fail("Two identical adjacent ports in trip is invalid.");
        }
        if (toFetchPortIds.size() != portsView.size() + 2) {
            return ResponseUtil.fail("Two identical ports in trip is invalid.");
        }

        Set<Long> toFetchStateroomIds = new HashSet<>();
        if (tripCreate.getRooms().isEmpty()) {
            return ResponseUtil.fail("At least one room should be specified.");
        }

        for (TripCreateParam.RoomPriceView priceView : tripCreate.getRooms()) {
            if (priceView.getPricePerNight() <= 0) {
                return ResponseUtil.fail("Price of room must be non-negative");
            }

            if (toFetchStateroomIds.contains(priceView.getStateroomId())) {
                return ResponseUtil.fail("Invalid re-define of same room.");
            }
            toFetchStateroomIds.add(priceView.getStateroomId());
        }

        List<PortEntity> portEntities = portRepository.findAllById(toFetchPortIds);
        List<StateroomEntity> stateroomEntities = stateroomRepository.findAllById(toFetchStateroomIds);
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAllById(tripCreate.getRestaurants());
        List<EntertainmentEntity> entertainmentEntities = entertainmentRepository.findAllById(tripCreate.getEntertainments());

        if (portEntities.size() != toFetchPortIds.size() ||
                stateroomEntities.size() != toFetchStateroomIds.size() ||
                restaurantEntities.size() != tripCreate.getRestaurants().size() ||
                entertainmentEntities.size() != tripCreate.getEntertainments().size()) {
            return ResponseUtil.fail("Find invalid record id, please refresh and retry create.");
        }

        Map<Long, PortEntity> portEntityMap = new HashMap<>();
        portEntities.forEach(x -> portEntityMap.put(x.getPortId(), x));
        Map<Long, StateroomEntity> stateroomEntityMap = new HashMap<>();
        stateroomEntities.forEach(x -> stateroomEntityMap.put(x.getStateroomId(), x));

        TripEntity tripEntity = new TripEntity();
        tripEntity.setTripId(0L);
        tripEntity.setTripNights(tripNights);
        tripEntity.setName(tripCreate.getTripName());
        tripEntity.setStatus(TripStatus.REGISTERING.name());
        tripEntity.setStartDate(tripCreate.getStartDate());
        tripEntity.setEndDate(tripCreate.getEndDate());

        tripEntity.setStartPort(portEntityMap.get(tripCreate.getStartPortId()));
        tripEntity.setEndPort(portEntityMap.get(tripCreate.getEndPortId()));


        tripEntity = tripRepository.saveAndFlush(tripEntity);


        tripEntity.setRestaurants(restaurantEntities);
        tripEntity.setEntertainments(entertainmentEntities);
        for (int i = 0; i < tripCreate.getPorts().size(); ++i) {
            var param = tripCreate.getPorts().get(i);
            TripPortInfoEntity portInfo = new TripPortInfoEntity();
            TripPortInfoId id = new TripPortInfoId();
            id.setTripId(tripEntity.getTripId());
            id.setPortId(param.getPortId());
            portInfo.setId(id);
            portInfo.setPort(portEntityMap.get(param.getPortId()));
            portInfo.setArrivalDate(param.getArrivalDate());
            portInfo.setDepartureDate(param.getDepartureDate());
            portInfo.setTrip(tripEntity);
            tripEntity.getTripPorts().add(portInfo);
        }
        for (int i = 0; i < tripCreate.getRooms().size(); ++i) {
            var rooms = tripCreate.getRooms().get(i);
            var price = new StateroomPriceEntity();
            price.setStateroom(stateroomEntityMap.get(rooms.getStateroomId()));
            price.setPriceNight(rooms.getPricePerNight());
            price.setTrip(tripEntity);
            StateroomPriceId id = new StateroomPriceId();
            id.setTripId(tripEntity.getTripId());
            id.setStateroomId(rooms.getStateroomId());
            price.setId(id);
            tripEntity.getStaterooms().add(price);
        }
        tripRepository.saveAndFlush(tripEntity);
        return ResponseEntity.ok(tripEntity.getTripId());
    }


    @Transactional
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody TripCreateParam tripCreate) {
        if (tripCreate.getTripId() == null) {
            return ResponseUtil.fail("Trip not exists");
        }

        if (StringUtils.isBlank(tripCreate.getTripName())) {
            return ResponseUtil.fail("Invalid Empty Trip Name");
        }

        Optional<TripEntity> existsOpt = tripRepository.findById(tripCreate.getTripId());
        if (existsOpt.isEmpty()) {
            return ResponseUtil.fail("Trip not exists");
        }
        TripEntity exists = existsOpt.get();
        if (!TripStatus.REGISTERING.name().equals(exists.getStatus())) {
            return ResponseUtil.fail("Only trip not started is available to update");
        }
        List<TripCreateParam.TripPortView> portsView = tripCreate.getPorts();
        Date lastDate = exists.getStartDate();
        Date endDate = exists.getEndDate();
        long lastId = exists.getStartPort().getPortId();

        Set<Long> toFetchPortIds = new HashSet<>();
        for (int i = 0; i < portsView.size(); ++i) {
            TripCreateParam.TripPortView step = portsView.get(i);
            if (step.getPortId() == lastId) {
                return ResponseUtil.fail("Two identical adjacent ports in trip is invalid.");
            }
            if (!step.getArrivalDate().after(lastDate) || !step.getArrivalDate().before(step.getDepartureDate()) || !step.getDepartureDate().before(endDate)) {
                return ResponseUtil.fail("Illegal arrival or departure date. At the No." + i + " Port");
            }
            lastId = step.getPortId();
            toFetchPortIds.add(step.getPortId());
        }
        if (portsView.size() > 0 && exists.getEndPort().getPortId() == lastId) {
            return ResponseUtil.fail("Two identical adjacent ports in trip is invalid.");
        }

        Set<Long> toFetchStateroomIds = new HashSet<>();
        if (tripCreate.getRooms().isEmpty()) {
            return ResponseUtil.fail("At least one room should be specified.");
        }

        for (TripCreateParam.RoomPriceView priceView : tripCreate.getRooms()) {
            if (priceView.getPricePerNight() <= 0) {
                return ResponseUtil.fail("Price of room must be non-negative");
            }

            if (toFetchStateroomIds.contains(priceView.getStateroomId())) {
                return ResponseUtil.fail("Invalid re-define of same room.");
            }
            toFetchStateroomIds.add(priceView.getStateroomId());
        }

        List<PortEntity> portEntities = portRepository.findAllById(toFetchPortIds);
        List<StateroomEntity> stateroomEntities = stateroomRepository.findAllById(toFetchStateroomIds);
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAllById(tripCreate.getRestaurants());
        List<EntertainmentEntity> entertainmentEntities = entertainmentRepository.findAllById(tripCreate.getEntertainments());

        if (portEntities.size() != toFetchPortIds.size() ||
                stateroomEntities.size() != toFetchStateroomIds.size() ||
                restaurantEntities.size() != tripCreate.getRestaurants().size() ||
                entertainmentEntities.size() != tripCreate.getEntertainments().size()) {
            return ResponseUtil.fail("Find invalid record id, please refresh and retry create.");
        }

        Map<Long, PortEntity> portEntityMap = new HashMap<>();
        portEntities.forEach(x -> portEntityMap.put(x.getPortId(), x));
        Map<Long, StateroomEntity> stateroomEntityMap = new HashMap<>();
        stateroomEntities.forEach(x -> stateroomEntityMap.put(x.getStateroomId(), x));

        exists.setName(tripCreate.getTripName());
        exists.setRestaurants(restaurantEntities);
        exists.setEntertainments(entertainmentEntities);
        exists.getTripPorts().clear();
        for (int i = 0; i < tripCreate.getPorts().size(); ++i) {
            var param = tripCreate.getPorts().get(i);
            TripPortInfoEntity portInfo = new TripPortInfoEntity();
            TripPortInfoId id = new TripPortInfoId();
            id.setTripId(exists.getTripId());
            id.setPortId(param.getPortId());
            portInfo.setId(id);
            portInfo.setPort(portEntityMap.get(param.getPortId()));
            portInfo.setArrivalDate(param.getArrivalDate());
            portInfo.setDepartureDate(param.getDepartureDate());
            portInfo.setTrip(exists);
            exists.getTripPorts().add(portInfo);
        }
        exists.getStaterooms().clear();
        for (int i = 0; i < tripCreate.getRooms().size(); ++i) {
            var rooms = tripCreate.getRooms().get(i);
            var price = new StateroomPriceEntity();
            price.setStateroom(stateroomEntityMap.get(rooms.getStateroomId()));
            price.setPriceNight(rooms.getPricePerNight());
            price.setTrip(exists);
            StateroomPriceId id = new StateroomPriceId();
            id.setTripId(exists.getTripId());
            id.setStateroomId(rooms.getStateroomId());
            price.setId(id);
            exists.getStaterooms().add(price);
        }
        tripRepository.saveAndFlush(exists);
        return ResponseEntity.ok(exists.getTripId());
    }


}
