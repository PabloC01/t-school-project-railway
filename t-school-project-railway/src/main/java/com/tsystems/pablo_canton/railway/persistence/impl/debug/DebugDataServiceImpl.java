package com.tsystems.pablo_canton.railway.persistence.impl.debug;

import com.tsystems.pablo_canton.railway.persistence.api.debug.IDebugDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.StationEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DebugDataServiceImpl implements IDebugDataService {

    private static final String HELLO_WORLD = "1) This is HELLO WORLD from DebugDataService\n";

    @Autowired
    private StationRepository stationRepository;


    public String findHelloWorld() {
        return HELLO_WORLD;
    }

    @Override
    public List<StationEntity> findAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public Collection<ScheduleEntity> findSchedulesByStartStationId(Integer id) {
        Optional<StationEntity> station = stationRepository.findById(id);
        if(station.isPresent()){
            return station.get().getSchedulesByStationId();
        }
        else return Collections.emptyList();
    }

}
