package com.tsystems.pablo_canton.railway.persistence.impl.employees;

import com.tsystems.pablo_canton.railway.exception.ResourceNotFoundException;
import com.tsystems.pablo_canton.railway.persistence.api.employees.IEmployeesDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import com.tsystems.pablo_canton.railway.persistence.jpa.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeesDataServiceImpl implements IEmployeesDataService {

    private final TrainRepository trainRepository;

    private final ScheduleRepository scheduleRepository;

    private final QueryRepository queryRepository;

    private final StationRepository stationRepository;

    private final WagonRepository wagonRepository;

    private final SeatRepository seatRepository;

    @Override
    public List<TrainEntity> findTrains() {
        return trainRepository.findAll();
    }

    @Override
    public List<ScheduleEntity> findSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<UserEntity> findPassengers(Integer scheduleId) {
        return queryRepository.findScheduleUsers(scheduleId);
    }

    @Override
    public StationEntity createStation(StationEntity station) {
        return stationRepository.save(station);
    }

    @Override
    public ScheduleEntity createSchedule(ScheduleEntity schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public TrainEntity createTrain(TrainEntity train) {
        return trainRepository.save(train);
    }

    @Override
    public WagonEntity createWagon(WagonEntity wagon) {
        return wagonRepository.save(wagon);
    }

    @Override
    public SeatEntity createSeat(SeatEntity seat) {
        return seatRepository.save(seat);
    }

    @Override
    public StationEntity loadStation(Integer id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found " + id));
    }

    @Override
    public TrainEntity loadTrain(Integer id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found " + id));
    }
}
