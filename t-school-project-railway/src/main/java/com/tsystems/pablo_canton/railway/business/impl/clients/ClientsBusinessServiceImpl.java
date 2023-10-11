package com.tsystems.pablo_canton.railway.business.impl.clients;

import com.tsystems.pablo_canton.railway.business.Converter;
import com.tsystems.pablo_canton.railway.business.api.clients.IClientsBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;
import com.tsystems.pablo_canton.railway.exception.DepartureTimeTooLateException;
import com.tsystems.pablo_canton.railway.exception.SeatNotFreeException;
import com.tsystems.pablo_canton.railway.exception.UserAlreadyHaveTicketException;
import com.tsystems.pablo_canton.railway.persistence.api.clients.IClientsDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.SeatEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.TicketEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientsBusinessServiceImpl implements IClientsBusinessService {

    private final IClientsDataService clientsDataService;

    private final Converter converter;
    @Override
    public List<ScheduleDTO> getSchedules(Integer station_a_id, Integer station_b_id, LocalDateTime start_time, LocalDateTime end_time) {
        return clientsDataService.findSchedules(station_a_id, station_b_id, start_time, end_time).stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getSchedulesByStationId(Integer station_id) {
        return clientsDataService.findSchedulesByStationId(station_id).stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public TicketDTO createTicket(TicketDTO dto) {
        TicketEntity ticket = new TicketEntity();

        SeatEntity seat = clientsDataService.loadSeat(dto.getSeat().getNumber(), dto.getSeat().getWagonNumber(), dto.getSeat().getTrainNumber());
        List<ScheduleEntity> seat_schedules = clientsDataService.findSeatTicketsSchedules(seat);
        ScheduleEntity schedule = clientsDataService.loadSchedule(dto.getScheduleByScheduleId().getScheduleId());

        if(seat_schedules.contains(schedule)){
            throw new SeatNotFreeException("Seat not free {" + dto.getSeat().getNumber() + "," + dto.getSeat().getWagonNumber() + "," + dto.getSeat().getTrainNumber() + "}");
        }

        ticket.setSeat(seat);
        ticket.setScheduleByScheduleId(schedule);

        UserEntity user = clientsDataService.loadUser(dto.getUserByUserId().getUserId());
        List<UserEntity> schedule_users = clientsDataService.findScheduleUsers(schedule.getScheduleId());

        if(schedule_users.contains(user)){
            throw new UserAlreadyHaveTicketException("User already have a ticket for the schedule " + user.getUserId());
        }

        ticket.setUserByUserId(user);

        LocalDateTime departure_time = schedule.getDepartureTime();

        if(LocalDateTime.now().isAfter(departure_time.minusMinutes(10))){
            throw new DepartureTimeTooLateException("Too late before departure of the train");
        }

        return converter.createTicketDto(clientsDataService.createTicket(ticket));
    }
}