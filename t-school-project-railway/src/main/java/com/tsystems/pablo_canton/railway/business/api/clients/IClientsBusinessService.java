package com.tsystems.pablo_canton.railway.business.api.clients;

import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;
import com.tsystems.pablo_canton.railway.business.dto.UserDTO;
import com.tsystems.pablo_canton.railway.business.dto.WagonInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsBusinessService {
    List<ScheduleDTO> getSchedules(String stationAName, String stationBName, LocalDateTime startTime, LocalDateTime endTime);

    List<ScheduleDTO> getSchedulesByStationName(String stationName);

    TicketDTO createTicket(TicketDTO dto);

    List<WagonInfo> getWagonsInfo(Integer trainNumber, Integer scheduleId);

    List<String> getStationNames();

    UserDTO getClientByUsername(String username);

    List<TicketDTO> getClientTickets(String username);
}
