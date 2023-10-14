package com.tsystems.pablo_canton.railway.persistence.api.employees;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;

import java.util.Collection;
import java.util.List;

public interface IEmployeesDataService {
    List<TrainEntity> findTrains();

    List<ScheduleEntity> findSchedules();

    List<UserEntity> findPassengers(Integer scheduleId);

    StationEntity createStation(StationEntity station);

    ScheduleEntity createSchedule(ScheduleEntity schedule);

    TrainEntity createTrain(TrainEntity train);

    WagonEntity createWagon(WagonEntity wagon);

    SeatEntity createSeat(SeatEntity seat);

    StationEntity loadStation(Integer id);

    TrainEntity loadTrain(Integer id);
}
