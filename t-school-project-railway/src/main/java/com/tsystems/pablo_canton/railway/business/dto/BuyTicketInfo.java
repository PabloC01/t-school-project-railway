package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

@Data
public class BuyTicketInfo {
    private String token;
    private SeatDTO seat;
    private ScheduleDTO schedule;
}
