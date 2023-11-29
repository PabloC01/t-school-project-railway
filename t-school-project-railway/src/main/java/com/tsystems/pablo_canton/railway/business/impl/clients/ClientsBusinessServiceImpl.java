package com.tsystems.pablo_canton.railway.business.impl.clients;

import com.tsystems.pablo_canton.railway.business.dto.*;
import com.tsystems.pablo_canton.railway.setup.exception.UserIsNotClientException;
import com.tsystems.pablo_canton.railway.setup.utils.Converter;
import com.tsystems.pablo_canton.railway.business.api.clients.IClientsBusinessService;
import com.tsystems.pablo_canton.railway.setup.exception.DepartureTimeTooLateException;
import com.tsystems.pablo_canton.railway.setup.exception.SeatNotFreeException;
import com.tsystems.pablo_canton.railway.setup.exception.UserAlreadyHaveTicketException;
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
    public List<ScheduleDTO> getSchedules(String stationAName, String stationBName, LocalDateTime startTime, LocalDateTime endTime) {
        return clientsDataService.findSchedules(stationAName, stationBName, startTime, endTime).stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getSchedulesByStationName(String stationName) {
        return clientsDataService.findSchedulesByStationName(stationName).stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public TicketDTO createTicket(TicketDTO dto) {
        SeatDTO seatDTO = dto.getSeat();
        SeatEntity seat = clientsDataService.loadSeat(seatDTO.getNumber(), seatDTO.getWagonNumber(), seatDTO.getTrainNumber());
        ScheduleEntity schedule = clientsDataService.loadSchedule(dto.getSchedule().getScheduleId());

        if(clientsDataService.isSeatBusy(seat, schedule)){
            throw new SeatNotFreeException("Seat not free {" + dto.getSeat().getNumber() + "," + dto.getSeat().getWagonNumber() + "," + dto.getSeat().getTrainNumber() + "}");
        }

        UserEntity user = clientsDataService.loadUser(dto.getUser().getUserId());

        if(clientsDataService.userAlreadyHaveTicket(user, schedule.getScheduleId())){
            throw new UserAlreadyHaveTicketException("User already have a ticket for the schedule " + schedule.getScheduleId());
        }

        if(!user.getRole().equals("C")){
            throw new UserIsNotClientException("User is not a client " + user.getUserId());
        }

        LocalDateTime departureTime = schedule.getDepartureTime();

        if(LocalDateTime.now().isAfter(departureTime.minusMinutes(10))){
            throw new DepartureTimeTooLateException("Too late before departure of the train");
        }

        TicketEntity ticket = new TicketEntity();
        ticket.setSeat(seat);
        ticket.setScheduleByScheduleId(schedule);
        ticket.setUserByUserId(user);

        return converter.createTicketDto(clientsDataService.createTicket(ticket));
    }

    @Override
    public List<WagonInfo> getWagonsInfo(Integer trainNumber, Integer scheduleId) {
        return clientsDataService.findWagonsInfo(trainNumber, scheduleId);
    }

    @Override
    public List<String> getStationNames() {
        return clientsDataService.findStationNames();
    }

    @Override
    public UserDTO getClientByUsername(String username) {
        return converter.createUserDTO(clientsDataService.loadUserByUsername(username));
    }

    @Override
    public List<TicketDTO> getClientTickets(String username) {
        return clientsDataService.findClientTickets(username).stream()
                .map(converter::createTicketDto)
                .toList();
    }
}