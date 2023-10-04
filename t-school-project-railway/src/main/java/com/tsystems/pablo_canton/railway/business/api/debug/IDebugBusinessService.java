package com.tsystems.pablo_canton.railway.business.api.debug;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.StationEntity;

import java.util.Collection;
import java.util.List;

public interface IDebugBusinessService {

    String getHelloWorld();

    List<StationEntity> getAllStations();

    Collection<ScheduleEntity> getSchedulesByStartStationId(Integer id);
}
