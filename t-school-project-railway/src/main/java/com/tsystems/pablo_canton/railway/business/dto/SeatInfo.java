package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatInfo {
    private Integer seatNumber;

    private boolean isAvailable;
}
