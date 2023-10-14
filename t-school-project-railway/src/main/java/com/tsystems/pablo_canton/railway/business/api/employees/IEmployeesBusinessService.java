package com.tsystems.pablo_canton.railway.business.api.employees;

import com.tsystems.pablo_canton.railway.business.dto.*;

import java.util.List;

public interface IEmployeesBusinessService {
    List<TrainDTO> getTrains();

    List<ScheduleDTO> getSchedules();

    List<UserDTO> getPassengers(Integer scheduleId);

    StationDTO createStation(StationDTO dto);

    ScheduleDTO createSchedule(ScheduleDTO dto);

    TrainDTO createTrain(TrainWagons dto);
}
