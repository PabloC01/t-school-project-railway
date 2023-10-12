package com.tsystems.pablo_canton.railway.business.api.clients;

import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsBusinessService {
    List<ScheduleDTO> getSchedules(Integer stationAId, Integer stationBId, LocalDateTime startTime, LocalDateTime endTime);

    List<ScheduleDTO> getSchedulesByStationId(Integer stationId);

    TicketDTO createTicket(TicketDTO dto);

    List<Boolean> getEmptySeats(Integer trainNumber, Integer wagonNumber, Integer scheduleId);
}
