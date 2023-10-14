package com.tsystems.pablo_canton.railway.persistence.impl.clients;

import com.tsystems.pablo_canton.railway.exception.ResourceNotFoundException;
import com.tsystems.pablo_canton.railway.persistence.api.clients.IClientsDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import com.tsystems.pablo_canton.railway.persistence.jpa.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientsDataServiceImpl implements IClientsDataService {

    private final QueryRepository queryRepository;

    private final ScheduleRepository scheduleRepository;

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final SeatRepository seatRepository;

    private final WagonRepository wagonRepository;

    @Override
    public List<ScheduleEntity> findSchedules(Integer stationAId, Integer stationBId, LocalDateTime startTime, LocalDateTime endTime) {
        return queryRepository.findSchedules(stationAId, stationBId, startTime, endTime);
    }

    @Override
    public List<ScheduleEntity> findSchedulesByStationId(Integer stationId) {
        return queryRepository.findSchedulesByStationId(stationId);
    }

    @Override
    public TicketEntity createTicket(TicketEntity ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Boolean> findEmptySeats(Integer trainNumber, Integer wagonNumber, Integer scheduleId) {
        List<SeatEntity> busySeats = queryRepository.findSeatsByScheduleAndWagon(trainNumber, wagonNumber, scheduleId);
        WagonEntity wagon = loadWagon(trainNumber, wagonNumber);

        List<Boolean> emptySeats = new ArrayList<>(Collections.nCopies(wagon.getSeatCount(), true));

        for(SeatEntity seat : busySeats){
            emptySeats.set(seat.getNumber()-1, false);
        }

        return emptySeats;
    }

    @Override
    public boolean isSeatBusy(SeatEntity seat, ScheduleEntity schedule) {
        List<ScheduleEntity> seatSchedules = queryRepository.findSeatTicketsSchedules(seat.getNumber(), seat.getWagonNumber(), seat.getTrainNumber());
        return seatSchedules.contains(schedule);
    }

    @Override
    public boolean userAlreadyHaveTicket(UserEntity user, Integer scheduleId) {
        List<UserEntity> scheduleUsers = queryRepository.findScheduleUsers(scheduleId);
        return scheduleUsers.contains(user);
    }

    @Override
    public SeatEntity loadSeat(Integer number, Integer wagonNumber, Integer trainNumber) {
        SeatEntityPK seatEntityPK = new SeatEntityPK();
        seatEntityPK.setNumber(number);
        seatEntityPK.setWagonNumber(wagonNumber);
        seatEntityPK.setTrainNumber(trainNumber);
        return seatRepository.findById(seatEntityPK)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found {" + number + "," + wagonNumber + "," + trainNumber + "}"));
    }

    @Override
    public UserEntity loadUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found " + id));
    }

    @Override
    public ScheduleEntity loadSchedule(Integer id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found " + id));
    }

    @Override
    public WagonEntity loadWagon(Integer trainNumber, Integer wagonNumber) {
        WagonEntityPK wagonEntityPK = new WagonEntityPK();
        wagonEntityPK.setWagonNumber(wagonNumber);
        wagonEntityPK.setTrainNumber(trainNumber);
        return wagonRepository.findById(wagonEntityPK)
                .orElseThrow(() -> new ResourceNotFoundException("Wagon not found {" + trainNumber + "," + wagonNumber + "}"));
    }
}