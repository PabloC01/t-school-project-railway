package com.tsystems.pablo_canton.railway.persistence.api.employees;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;

import java.util.Collection;
import java.util.List;

public interface IEmployeesDataService {
    List<TrainEntity> findTrains();

    List<ScheduleEntity> findSchedules();

    List<StationEntity> findStations();

    List<UserEntity> findPassengers(Integer scheduleId);

    StationEntity createStation(StationEntity station);

    ScheduleEntity createSchedule(ScheduleEntity schedule);

    TrainEntity createTrain(TrainEntity train);

    WagonEntity createWagon(WagonEntity wagon);

    void createSeat(SeatEntity seat);

    StationEntity loadStation(String name);

    TrainEntity loadTrain(Integer number);

    boolean isTrainNumberAvailable(Integer trainNumber);

    boolean isStationNameAvailable(String name);

    List<String> findStationNames();
}
