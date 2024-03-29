package com.tsystems.pablo_canton.railway.persistence.impl.employees;

import com.tsystems.pablo_canton.railway.setup.exception.ResourceNotFoundException;
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
    public List<StationEntity> findStations() {
        return stationRepository.findAll();
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
    public void createSeat(SeatEntity seat) {
        seatRepository.save(seat);
    }

    @Override
    public StationEntity loadStation(String name) {
        return stationRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found " + name));
    }

    @Override
    public TrainEntity loadTrain(Integer number) {
        return trainRepository.findByNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found " + number));
    }

    @Override
    public boolean isTrainNumberAvailable(Integer trainNumber) {
        List<Integer> trainNumbers = queryRepository.findTrainNumbers();
        return trainNumbers.contains(trainNumber);
    }

    @Override
    public boolean isStationNameAvailable(String name) {
        List<String> stationNames = queryRepository.findStationNames();
        return stationNames.contains(name);
    }

    @Override
    public List<String> findStationNames() {
        return queryRepository.findStationNames();
    }
}
