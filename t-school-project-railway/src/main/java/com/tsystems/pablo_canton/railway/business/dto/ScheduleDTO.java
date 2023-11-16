package com.tsystems.pablo_canton.railway.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDTO {
    private Integer scheduleId;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime  arrivalTime;
    private TrainDTO train;
    private StationDTO startStation;
    private StationDTO endStation;
}
