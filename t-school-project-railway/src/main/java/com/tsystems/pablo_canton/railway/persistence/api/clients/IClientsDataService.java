package com.tsystems.pablo_canton.railway.persistence.api.clients;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.SeatEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.TicketEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IClientsDataService {
    List<ScheduleEntity> findSchedules(Integer station_a_id, Integer station_b_id, LocalDateTime start_time, LocalDateTime end_time);

    List<ScheduleEntity> findSchedulesByStationId(Integer station_id);

    TicketEntity createTicket(TicketEntity ticket);

    List<ScheduleEntity> findSeatTicketsSchedules(SeatEntity seat);

    List<UserEntity> findScheduleUsers(Integer schedule_id);

    SeatEntity loadSeat(Integer number, Integer wagon_number, Integer train_number);

    UserEntity loadUser(Integer id);

    ScheduleEntity loadSchedule(Integer id);
}
