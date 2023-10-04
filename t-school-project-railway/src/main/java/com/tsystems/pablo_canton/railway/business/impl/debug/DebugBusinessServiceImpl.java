package com.tsystems.pablo_canton.railway.business.impl.debug;

import com.tsystems.pablo_canton.railway.business.api.debug.IDebugBusinessService;
import com.tsystems.pablo_canton.railway.persistence.api.debug.IDebugDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DebugBusinessServiceImpl implements IDebugBusinessService {

    private static final String HELLO_WORLD = "2) This is HELLO WORLD from DebugBusinessService\n";


    private final IDebugDataService debugDataService;

    @Autowired
    public DebugBusinessServiceImpl(final IDebugDataService debugDataService) {
        this.debugDataService = debugDataService;
    }

    @Override
    public String getHelloWorld() {
        final var helloWorldString = debugDataService.findHelloWorld();
        return HELLO_WORLD + (helloWorldString == null ? "" : helloWorldString);
    }

    @Override
    public List<StationEntity> getAllStations() {
        return debugDataService.findAllStations();
    }

    @Override
    public Collection<ScheduleEntity> getSchedulesByStartStationId(Integer id) {
        return debugDataService.findSchedulesByStartStationId(id);
    }
}
