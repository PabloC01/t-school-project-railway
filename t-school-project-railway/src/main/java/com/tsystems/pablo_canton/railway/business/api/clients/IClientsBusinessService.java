package com.tsystems.pablo_canton.railway.business.api.clients;

import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.SeatInfo;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsBusinessService {
    List<ScheduleDTO> getSchedules(Integer stationAId, Integer stationBId, LocalDateTime startTime, LocalDateTime endTime);

    List<ScheduleDTO> getSchedulesByStationName(String stationName);

    TicketDTO createTicket(TicketDTO dto);

    List<SeatInfo> getEmptySeats(Integer trainNumber, Integer wagonNumber, Integer scheduleId);

    List<String> getStationNames();
}
