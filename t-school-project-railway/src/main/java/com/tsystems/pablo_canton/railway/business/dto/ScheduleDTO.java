package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDTO {
    private Integer scheduleId;
    private LocalDateTime departureTime;
    private LocalDateTime  arrivalTime;
    private TrainDTO train;
    private StationDTO startStation;
    private StationDTO endStation;
}
