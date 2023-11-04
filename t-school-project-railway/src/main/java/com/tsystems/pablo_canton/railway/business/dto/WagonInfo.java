package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

import java.util.List;

@Data
public class WagonInfo {
    private Integer number;
    private Integer seatCount;
    private Integer availableSeats;
    private Integer seatsPerRow;
    private List<SeatInfo> seats;
}

