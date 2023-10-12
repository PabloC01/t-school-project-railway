package com.tsystems.pablo_canton.railway.persistence.api.clients;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsDataService {
    List<ScheduleEntity> findSchedules(Integer stationAId, Integer stationBId, LocalDateTime startTime, LocalDateTime endTime);

    List<ScheduleEntity> findSchedulesByStationId(Integer stationId);

    TicketEntity createTicket(TicketEntity ticket);

    List<Boolean> findEmptySeats(Integer trainNumber, Integer wagonNumber, Integer scheduleId);

    boolean isSeatBusy(SeatEntity seat, ScheduleEntity schedule);

    boolean userAlreadyHaveTicket(UserEntity user, Integer scheduleId);

    SeatEntity loadSeat(Integer number, Integer wagonNumber, Integer trainNumber);

    UserEntity loadUser(Integer id);

    ScheduleEntity loadSchedule(Integer id);

    WagonEntity loadWagon(Integer trainNumber, Integer wagonNumber);
}
