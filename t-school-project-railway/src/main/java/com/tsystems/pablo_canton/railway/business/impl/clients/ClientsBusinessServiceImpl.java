package com.tsystems.pablo_canton.railway.business.impl.clients;

import com.tsystems.pablo_canton.railway.business.dto.SeatDTO;
import com.tsystems.pablo_canton.railway.exception.UserIsNotClientException;
import com.tsystems.pablo_canton.railway.utils.Converter;
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
    public List<ScheduleDTO> getSchedules(Integer stationAId, Integer stationBId, LocalDateTime startTime, LocalDateTime endTime) {
        return clientsDataService.findSchedules(stationAId, stationBId, startTime, endTime).stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getSchedulesByStationId(Integer stationId) {
        return clientsDataService.findSchedulesByStationId(stationId).stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public TicketDTO createTicket(TicketDTO dto) {
        SeatDTO seatDTO = dto.getSeat();
        SeatEntity seat = clientsDataService.loadSeat(seatDTO.getNumber(), seatDTO.getWagonNumber(), seatDTO.getTrainNumber());
        ScheduleEntity schedule = clientsDataService.loadSchedule(dto.getScheduleByScheduleId().getScheduleId());

        if(clientsDataService.isSeatBusy(seat, schedule)){
            throw new SeatNotFreeException("Seat not free {" + dto.getSeat().getNumber() + "," + dto.getSeat().getWagonNumber() + "," + dto.getSeat().getTrainNumber() + "}");
        }

        UserEntity user = clientsDataService.loadUser(dto.getUserByUserId().getUserId());

        if(clientsDataService.userAlreadyHaveTicket(user, schedule.getScheduleId())){
            throw new UserAlreadyHaveTicketException("User already have a ticket for the schedule " + user.getUserId());
        }

        if(!user.getRole().equals("C")){
            throw new UserIsNotClientException("User is not a client " + user.getUserId());
        }

        LocalDateTime departure_time = schedule.getDepartureTime();

        if(LocalDateTime.now().isAfter(departure_time.minusMinutes(10))){
            throw new DepartureTimeTooLateException("Too late before departure of the train");
        }

        TicketEntity ticket = new TicketEntity();
        ticket.setSeat(seat);
        ticket.setScheduleByScheduleId(schedule);
        ticket.setUserByUserId(user);

        return converter.createTicketDto(clientsDataService.createTicket(ticket));
    }

    @Override
    public List<Boolean> getEmptySeats(Integer trainNumber, Integer wagonNumber, Integer scheduleId) {
        return clientsDataService.findEmptySeats(trainNumber, wagonNumber, scheduleId);
    }
}