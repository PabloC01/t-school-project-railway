package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private Integer ticketId;
    private UserDTO userByUserId;
    private SeatDTO seat;
    private ScheduleDTO scheduleByScheduleId;
}