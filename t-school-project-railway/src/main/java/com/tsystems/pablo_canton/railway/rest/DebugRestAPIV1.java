package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.api.debug.IDebugBusinessService;
import com.tsystems.pablo_canton.railway.persistence.dto.DebugResponse;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v1/debug")
public class DebugRestAPIV1 {

    private static final String HELLO_WORLD = "3) This is HELLO WORLD from DebugRestAPIV1\n";
    private final IDebugBusinessService debugBusinessService;

    @Autowired
    public DebugRestAPIV1(IDebugBusinessService debugBusinessService) {
        this.debugBusinessService = debugBusinessService;
    }

    @GetMapping("/hello-world-string")
    public String helloWorldString() {
        final var helloWorld = debugBusinessService.getHelloWorld();
        return HELLO_WORLD + (helloWorld == null ? "" : helloWorld);
    }

    @GetMapping("/hello-world-json")
    public DebugResponse helloWorldJson() {
        final var helloWorldStringFromBusinessService = debugBusinessService.getHelloWorld();
        final var resultString = HELLO_WORLD + (helloWorldStringFromBusinessService == null ? "" : helloWorldStringFromBusinessService);
        return DebugResponse.builder().debugResponse(resultString).build();
    }

    @GetMapping("/stations")
    public List<StationEntity> getAllStations(){
        return debugBusinessService.getAllStations();
    }

    @GetMapping("/schedulesByStartStation/{id}")
    public Collection<ScheduleEntity> getSchedulesByStartStation(@PathVariable Integer id) {return debugBusinessService.getSchedulesByStartStationId(id);}
}
