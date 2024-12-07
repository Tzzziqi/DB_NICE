package com.edu.trip.params;

import lombok.Data;

@Data
public class ChangeTripStatusParam {

    private Long tripId;
    private String status;

}
