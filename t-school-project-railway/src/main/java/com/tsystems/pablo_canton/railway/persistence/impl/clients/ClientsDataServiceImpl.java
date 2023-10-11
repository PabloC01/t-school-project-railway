package com.tsystems.pablo_canton.railway.persistence.impl.clients;

import com.tsystems.pablo_canton.railway.exception.ResourceNotFoundException;
import com.tsystems.pablo_canton.railway.persistence.api.clients.IClientsDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import com.tsystems.pablo_canton.railway.persistence.jpa.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<ScheduleEntity> findSchedules(Integer station_start_id, Integer station_end_id, LocalDateTime start_time, LocalDateTime end_time) {
        return queryRepository.findSchedules(station_start_id, station_end_id, start_time, end_time);
    }

    @Override
    public List<ScheduleEntity> findSchedulesByStationId(Integer station_id) {
        return queryRepository.findSchedulesByStationId(station_id);
    }

    @Override
    public TicketEntity createTicket(TicketEntity ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<ScheduleEntity> findSeatTicketsSchedules(SeatEntity seat) {
        return queryRepository.findSeatTicketsSchedules(seat.getNumber(), seat.getWagonNumber(), seat.getTrainNumber());
    }

    @Override
    public List<UserEntity> findScheduleUsers(Integer schedule_id) {
        return queryRepository.findScheduleUsers(schedule_id);
    }

    @Override
    public SeatEntity loadSeat(Integer number, Integer wagon_number, Integer train_number) {
        SeatEntityPK seatEntityPK = new SeatEntityPK();
        seatEntityPK.setNumber(number);
        seatEntityPK.setWagonNumber(wagon_number);
        seatEntityPK.setTrainNumber(train_number);
        return seatRepository.findById(seatEntityPK)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found {" + number + "," + wagon_number + "," + train_number + "}"));
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
}
