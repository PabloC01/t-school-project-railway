package com.tsystems.pablo_canton.railway.business.api.employees;

import com.tsystems.pablo_canton.railway.business.dto.*;

import java.util.List;

public interface IEmployeesBusinessService {
    List<TrainInfo> getTrains();

    List<ScheduleDTO> getSchedules();

    List<StationDTO> getStations();

    List<UserInfo> getPassengers(Integer scheduleId);

    StationDTO createStation(StationDTO dto);

    ScheduleDTO createSchedule(ScheduleDTO dto);

    TrainDTO createTrain(TrainWagons dto);

    List<String> getStationNames();


}
