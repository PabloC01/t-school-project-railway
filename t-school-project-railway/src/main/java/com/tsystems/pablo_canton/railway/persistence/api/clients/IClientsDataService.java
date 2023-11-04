package com.tsystems.pablo_canton.railway.persistence.api.clients;

import com.tsystems.pablo_canton.railway.business.dto.WagonInfo;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsDataService {
    List<ScheduleEntity> findSchedules(String stationAName, String stationBName, LocalDateTime startTime, LocalDateTime endTime);

    List<ScheduleEntity> findSchedulesByStationName(String stationName);

    List<String> findStationNames();

    TicketEntity createTicket(TicketEntity ticket);

    List<WagonInfo> findWagonsInfo(Integer trainNumber, Integer scheduleId);

    List<TicketEntity> findClientTickets(String username);

    boolean isSeatBusy(SeatEntity seat, ScheduleEntity schedule);

    boolean userAlreadyHaveTicket(UserEntity user, Integer scheduleId);

    SeatEntity loadSeat(Integer number, Integer wagonNumber, Integer trainNumber);

    UserEntity loadUser(Integer id);

    UserEntity loadUserByUsername(String username);

    ScheduleEntity loadSchedule(Integer id);
}
