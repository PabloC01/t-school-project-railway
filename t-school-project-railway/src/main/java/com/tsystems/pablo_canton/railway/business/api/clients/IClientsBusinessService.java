package com.tsystems.pablo_canton.railway.business.api.clients;

import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsBusinessService {
    List<ScheduleDTO> getSchedules(Integer station_a_id, Integer station_b_id, LocalDateTime start_time, LocalDateTime end_time);

    List<ScheduleDTO> getSchedulesByStationId(Integer station_id);

    TicketDTO createTicket(TicketDTO dto);
}
