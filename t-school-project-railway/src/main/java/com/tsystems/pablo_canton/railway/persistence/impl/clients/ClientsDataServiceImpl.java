package com.tsystems.pablo_canton.railway.persistence.impl.clients;

import com.tsystems.pablo_canton.railway.business.dto.SeatInfo;
import com.tsystems.pablo_canton.railway.business.dto.WagonInfo;
import com.tsystems.pablo_canton.railway.setup.exception.ResourceNotFoundException;
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

    @Override
    public List<ScheduleEntity> findSchedules(String stationAName, String stationBName, LocalDateTime startTime, LocalDateTime endTime) {
        return queryRepository.findSchedules(stationAName, stationBName, startTime, endTime);
    }

    @Override
    public List<ScheduleEntity> findSchedulesByStationName(String stationName) {
        return queryRepository.findSchedulesByStationName(stationName);
    }

    @Override
    public List<String> findStationNames() {
        return queryRepository.findStationNames();
    }

    @Override
    public TicketEntity createTicket(TicketEntity ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<WagonInfo> findWagonsInfo(Integer trainNumber, Integer scheduleId) {
        List<WagonEntity> wagons = queryRepository.findTrainWagons(trainNumber);
        List<WagonInfo> wagonsInfo = new ArrayList<>();

        for(WagonEntity wagon : wagons){
            List<SeatEntity> busySeats = queryRepository.findSeatsByScheduleAndWagon(trainNumber, wagon.getWagonNumber(), scheduleId);

            List<Boolean> seats = new ArrayList<>(Collections.nCopies(wagon.getSeatCount(), true));

            Integer availableSeats = wagon.getSeatCount();
            for(SeatEntity seat : busySeats){
                seats.set(seat.getNumber()-1, false);
                availableSeats--;
            }

            WagonInfo wagonInfo = new WagonInfo();
            wagonInfo.setNumber(wagon.getWagonNumber());
            wagonInfo.setSeatCount(wagon.getSeatCount());
            wagonInfo.setAvailableSeats(availableSeats);
            wagonInfo.setSeatsPerRow(wagon.getSeatPerRow());
            wagonInfo.setSeats(createSeatInfoList(seats));

            wagonsInfo.add(wagonInfo);
        }

        return wagonsInfo;
    }

    @Override
    public List<TicketEntity> findClientTickets(String username) {
        return queryRepository.findClientTickets(username);
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
    public UserEntity loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + username));
    }

    @Override
    public ScheduleEntity loadSchedule(Integer id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found " + id));
    }

    public List<SeatInfo> createSeatInfoList(List<Boolean> seats){
        List<SeatInfo> seatInfoList = new ArrayList<>();

        for(int i=0; i<seats.size(); i++){
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setSeatNumber(i+1);
            seatInfo.setAvailable(seats.get(i));
            seatInfoList.add(seatInfo);
        }

        return seatInfoList;
    }
}
