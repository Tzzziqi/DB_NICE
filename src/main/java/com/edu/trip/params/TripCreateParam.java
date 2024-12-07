package com.edu.trip.params;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TripCreateParam {

    private Long tripId;

    private String tripName;

    private Date startDate;

    private Date endDate;

    private long startPortId;

    private long endPortId;

    private List<TripPortView> ports = new ArrayList<>();

    private List<RoomPriceView> rooms = new ArrayList<>();

    private List<Long> restaurants = new ArrayList<>();

    private List<Long> entertainments = new ArrayList<>();


    @Data
    public static class RoomPriceView {
        private long stateroomId;
        private int pricePerNight;
    }

    @Data
    public static class TripPortView {
        private long portId;
        private Date arrivalDate;
        private Date departureDate;

    }

}
