package com.tsystems.pablo_canton.railway.persistence.api.debug;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.StationEntity;

import java.util.Collection;
import java.util.List;

public interface IDebugDataService {
    String findHelloWorld();

    List<StationEntity> findAllStations();

    Collection<ScheduleEntity> findSchedulesByStartStationId(Integer id);
}
