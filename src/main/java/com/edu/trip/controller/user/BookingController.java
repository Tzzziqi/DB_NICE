package com.edu.trip.controller.user;

import com.edu.trip.data_view.BookingView;
import com.edu.trip.jpa.*;
import com.edu.trip.model.*;
import com.edu.trip.params.BookingCreateParam;
import com.edu.trip.po.ChargeType;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.po.TripStatus;
import com.edu.trip.po.UserGender;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/customer/booking")
@RestController
public class BookingController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/read")
    public ResponseEntity read(
            @RequestAttribute(value = "userId") Long userId,
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<BookingEntity> bookings = StringUtils.isBlank(keyword) ? bookingRepository.findAllByUserId(userId) : bookingRepository.findAllTripByUser(userId, keyword);
        return ResponseUtil.ok(bookings.stream().map(x -> new BookingView(x, x.getTrip(), new ArrayList<>())).collect(Collectors.toList()));
    }

    @GetMapping("/readSingle")
    public ResponseEntity readSingle(
            @RequestAttribute(value = "userId") Long userId,
            @RequestParam("bookingId") long bookingId) {
        Optional<BookingEntity> bookingOpt = bookingRepository.findBookAndTripById(bookingId);
        if (bookingOpt.isEmpty()) {
            return ResponseUtil.fail("Booking not exists");
        }
        BookingEntity booking = bookingOpt.get();
        if (booking.getUser().getId() != userId) {
            return ResponseUtil.fail("Booking not exists");
        }
        List<BookingEntity> allBookings = bookingRepository.findAllByTrip(booking.getTrip().getTripId());
        List<Long> bookedRoom = new ArrayList<>();
        allBookings.forEach(x -> x.getStaterooms().forEach(r -> bookedRoom.add(r.getStateroomId())));
        return ResponseUtil.ok(new BookingView(booking, booking.getTrip(), bookedRoom));
    }

    @Transactional
    @PostMapping("/delete")
    public ResponseEntity delete(
            @RequestAttribute(value = "userId") long userId,
            @RequestParam("bookingId") long bookingId) {
        Optional<BookingEntity> bookingOpt = bookingRepository.findBookAndTripById(bookingId);
        if (bookingOpt.isEmpty()) {
            return ResponseUtil.fail("Booking not exists");
        }
        BookingEntity booking = bookingOpt.get();
        if (booking.getUser().getId() != userId) {
            return ResponseUtil.fail("Booking not exists");
        }
        if (!TripStatus.REGISTERING.name().equals(booking.getTrip().getStatus())) {
            return ResponseUtil.fail("Invalid booking status");
        }
        bookingRepository.delete(booking);
        return ResponseUtil.ok(null);
    }

    private ResponseEntity commonCheck(BookingCreateParam param) {
        if (param.getPassengers().isEmpty()) {
            return ResponseUtil.fail("Passenger must be specified");
        }


        Set<Long> toBookRoomIdSet = new HashSet<>(param.getStaterooms());
        if (toBookRoomIdSet.size() != param.getStaterooms().size()) {
            return ResponseUtil.fail("Invalid params");
        }
        List<PassengerEntity> passengers = param.getPassengers();
        for (PassengerEntity entity : passengers) {
            if (
                    StringUtils.isBlank(entity.getFirstName()) ||
                            StringUtils.isBlank(entity.getLastName()) ||
                            StringUtils.isBlank(entity.getEmail()) ||
                            StringUtils.isBlank(entity.getPhone()) ||
                            StringUtils.isBlank(entity.getCountry()) ||
                            StringUtils.isBlank(entity.getGender())
            ) {
                return ResponseUtil.fail("Required information of passenger is required");
            }
            if (!entity.getDob().before(new Date())) {
                return ResponseUtil.fail("Birthday of Passenger: " + entity.getFirstName() + " " + entity.getLastName() + " is invalid, must be less than now");
            }
            try {
                UserGender.valueOf(entity.getGender());
            } catch (Throwable e) {
                return ResponseUtil.fail("Invalid gender");
            }
            entity.setPassengerId(0L);

        }
        return null;
    }

    private int getChargePassengersCount(List<PassengerEntity> passengers, int age) {
        int totalChargePassengerCount = 0;
        for (PassengerEntity entity : passengers) {
            long time = new Date().getTime() - entity.getDob().getTime();
            time /= 365L * 24 * 60 * 60 * 1000L;
            if (time >= age) {
                totalChargePassengerCount++;
            }
        }
        return totalChargePassengerCount;
    }

    @Transactional
    public ResponseEntity dbBaseCheck(BookingCreateParam param, TripEntity tripEntity, Set<Long> ignoreRoom, List<StateroomEntity> toBookRoom, List<PackageEntity> packages) {
        List<BookingEntity> bookings = bookingRepository.findAllByTrip(tripEntity.getTripId());
        List<StateroomPriceEntity> staterooms = tripEntity.getStaterooms();
        Set<Long> bookedRoom = new HashSet<>();
        bookings.forEach(b -> b.getStaterooms().forEach(s -> bookedRoom.add(s.getStateroomId())));
        Map<Long, StateroomEntity> stateroomMap = new HashMap<>();
        staterooms.forEach(x -> stateroomMap.put(x.getId().getStateroomId(), x.getStateroom()));
        Map<Long, PackageEntity> packagesMapping = new HashMap<>();
        packageRepository.findAllByStatus(ResourceStatus.ACTIVE.name())
                .forEach(x -> packagesMapping.put(x.getPackageId(), x));
        Set<Long> existsPackageId = new HashSet<>();
        for (int i = 0; i < param.getPackages().size(); ++i) {
            long id = param.getPackages().get(i);
            if (!packagesMapping.containsKey(id)) {
                return ResponseUtil.fail("Invalid package not found");
            }
            if (existsPackageId.contains(id)) {
                return ResponseUtil.fail("No need to buy package twice");
            }
            existsPackageId.add(id);
            packages.add(packagesMapping.get(id));
        }
        for (int i = 0; i < param.getStaterooms().size(); ++i) {
            long roomId = param.getStaterooms().get(i);
            StateroomEntity entity = stateroomMap.get(roomId);
            if (entity == null) {
                return ResponseUtil.fail("Room not eixsts");
            }
            if (ignoreRoom.contains(roomId)) {
                toBookRoom.add(entity);
                continue;
            }
            if (bookedRoom.contains(roomId)) {
                return ResponseUtil.fail(entity.getStateroomType() + "_" + entity.getLocation().getLocation() + " is ordered already");
            }
            toBookRoom.add(entity);
        }
        return null;
    }

    @Transactional
    @PostMapping("/update")
    public ResponseEntity update(
            @RequestAttribute(value = "userId") Long userId,
            @RequestBody BookingCreateParam param
    ) {
        if (param.getBookingId() == null) {
            return ResponseUtil.fail("Booking not exists");
        }
        ResponseEntity preCheck = commonCheck(param);
        if (preCheck != null) {
            return preCheck;
        }
        int totalChargePassengerCount = getChargePassengersCount(param.getPassengers(), 5);

        Optional<BookingEntity> bookingOpt = bookingRepository.findById(param.getBookingId());
        if (bookingOpt.isEmpty()) {
            return ResponseUtil.fail("Booking not exists");
        }

        BookingEntity toSave = bookingOpt.get();
        if (toSave.getUser().getId() != userId) {
            return ResponseUtil.fail("Booking not exists");
        }
        if (invoiceRepository.countAllByBookingAndPaymentIsNull(toSave) > 0) {
            return ResponseUtil.fail("Please pay for previous invoice then update");
        }
        TripEntity tripEntity = toSave.getTrip();
        if (tripEntity.getTripNights() > 0) {
            if (param.getStaterooms().isEmpty()) {
                return ResponseUtil.fail("Room is required for trip nights > 0");
            }
        }
        if (TripStatus.FINISHED.name().equals(tripEntity.getStatus())) {
            return ResponseUtil.fail("Invalid booking status");
        }
        boolean onlyUpdatePackage = TripStatus.STARTED.name().equals(tripEntity.getStatus());

        List<StateroomEntity> toBookRoom = new ArrayList<>();
        List<PackageEntity> packages = new ArrayList<>();

        preCheck = dbBaseCheck(param, tripEntity, toSave.getStaterooms().stream().map(StateroomEntity::getStateroomId).collect(Collectors.toSet()), toBookRoom, packages);
        if (preCheck != null) {
            return preCheck;
        }
        int availBeds = 0;
        for (StateroomEntity s : toBookRoom) {
            availBeds += s.getNumberBeds();
        }
        if (totalChargePassengerCount > availBeds) {
            return ResponseUtil.fail("Too many guests aged 5 and up. You should book more staterooms. Now passengers aged 5 and up: " + totalChargePassengerCount + " Avail Beds: " + availBeds);
        }

        long prevMoney = calcMoney(tripEntity, toSave);
        toSave.getPackages().clear();
        toSave.setPackages(packages);
        if (!onlyUpdatePackage) {
            toSave.getStaterooms().clear();
            toSave.setStaterooms(toBookRoom);

            GroupEntity groupEntity = toSave.getGroup();
            for (PassengerEntity passenger : param.getPassengers()) {
                passenger.setGroup(groupEntity);
                passenger.setPassengerId(0L);
            }
            groupEntity.getPassengers().clear();
            groupEntity.getPassengers().addAll(param.getPassengers());
        }
        toSave = bookingRepository.saveAndFlush(toSave);
        long afterMoney = calcMoney(tripEntity, toSave);

        long diff = afterMoney - prevMoney;
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setStatus(0);
        invoiceEntity.setBooking(toSave);
        invoiceEntity.setMoney(diff);
        invoiceEntity.setInvoiceId(0L);
        invoiceEntity.setName("BookingUpdate");
        invoiceEntity.setDescription(
                "Update bookings: " + toBookRoom.size() + "rooms, " + param.getPassengers().size() + " passengers, " + packages.size() + " packages"
        );
        if (diff <= 0) {
            PaymentEntity payment = new PaymentEntity();
            payment.setPaymentId(0L);
            payment.setBooking(toSave);
            payment.setPaymentAmount(diff);
            payment.setPaymentDate(new Date());
            payment.setInvoice(invoiceEntity);
            payment.setPaymentMethod("Refund");
            invoiceEntity.setPayment(payment);
        }
        invoiceRepository.saveAndFlush(invoiceEntity);
        return ResponseUtil.ok(null);
    }


    @Transactional
    @PostMapping("/create")
    public ResponseEntity create(
            @RequestAttribute(value = "userId") Long userId,
            @RequestBody BookingCreateParam param
    ) {
        if (param.getTripId() == null) {
            return ResponseUtil.fail("Trip not exists");
        }
        ResponseEntity preCheck = commonCheck(param);
        if (preCheck != null) {
            return preCheck;
        }
        int totalChargePassengerCount = getChargePassengersCount(param.getPassengers(), 5);
        Optional<TripEntity> tripOpt = tripRepository.findById(param.getTripId());
        if (tripOpt.isEmpty()) {
            return ResponseUtil.fail("Trip not exists");
        }

        TripEntity tripEntity = tripOpt.get();
        if (!TripStatus.REGISTERING.name().equals(tripEntity.getStatus())) {
            return ResponseUtil.fail("Trip status invalid");
        }
        if (tripEntity.getTripNights() > 0) {
            if (param.getStaterooms().isEmpty()) {
                return ResponseUtil.fail("Room is required for trip nights > 0");
            }
        }
        List<StateroomEntity> toBookRoom = new ArrayList<>();
        List<PackageEntity> packages = new ArrayList<>();
        preCheck = dbBaseCheck(param, tripEntity, new HashSet<>(), toBookRoom, packages);
        if (preCheck != null) {
            return preCheck;
        }
        int availBeds = 0;
        for (StateroomEntity s : toBookRoom) {
            availBeds += s.getNumberBeds();
        }
        if (totalChargePassengerCount > availBeds) {
            return ResponseUtil.fail("Too many guests aged 5 and up. You should book more staterooms. Now passengers aged 5 and up: " + totalChargePassengerCount + " Avail Beds: " + availBeds);
        }

        BookingEntity toSave = new BookingEntity();
        toSave.setBookingId(0L);
        toSave.setTrip(tripEntity);
        toSave.setUser(userRepository.findById(userId).get());
        toSave.setStatus(ResourceStatus.ACTIVE.name());
        toSave.setPackages(packages);
        toSave.setStaterooms(toBookRoom);

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupId(0L);
        for (PassengerEntity passenger : param.getPassengers()) {
            passenger.setGroup(groupEntity);
            passenger.setPassengerId(0L);
        }
        groupEntity.setPassengers(param.getPassengers());
        groupEntity.setBooking(toSave);
        toSave.setGroup(groupEntity);
        toSave = bookingRepository.saveAndFlush(toSave);


        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setStatus(0);
        invoiceEntity.setBooking(toSave);
        invoiceEntity.setMoney(calcMoney(tripEntity, toSave));
        invoiceEntity.setInvoiceId(0L);
        invoiceEntity.setName("Basic Bill");
        invoiceEntity.setDescription("The initial bill: " + toBookRoom.size() + "rooms, " + param.getPassengers().size() + " passengers, " + packages.size() + " packages");
        invoiceRepository.saveAndFlush(invoiceEntity);
        return ResponseUtil.ok(null);
    }

    private long calcMoney(TripEntity trip, BookingEntity booking) {
        Map<Long, Long> roomPrice = new HashMap<>();
        trip.getStaterooms().forEach(x -> roomPrice.put(x.getId().getStateroomId(), (long) x.getPriceNight()));
        long price = 0L;
        for (StateroomEntity stateroom : booking.getStaterooms()) {
            price += roomPrice.get(stateroom.getStateroomId());
        }
        int ageOver5 = getChargePassengersCount(booking.getGroup().getPassengers(), 5);
        int ageOver18 = getChargePassengersCount(booking.getGroup().getPassengers(), 18);
        for (PackageEntity pkg : booking.getPackages()) {
            if (ChargeType.TRIP.name().equals(pkg.getChargeType())) {
                price += (long) ageOver5 * pkg.getPrice();
            } else {
                if ("Unlimited Bar".equals(pkg.getName())) {
                    price += (long) ageOver18 * pkg.getPrice() * trip.getTripNights();
                } else {
                    price += (long) ageOver5 * pkg.getPrice() * trip.getTripNights();
                }
            }
        }

        return price;
    }

}
