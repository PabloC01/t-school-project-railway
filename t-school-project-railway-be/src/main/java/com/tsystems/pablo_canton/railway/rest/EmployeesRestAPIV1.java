package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.api.employees.IEmployeesBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employees", description = "Employees operations management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employee")
public class EmployeesRestAPIV1 {

    private final IEmployeesBusinessService employeesBusinessService;

    @GetMapping(value = "/trains")
    public List<TrainInfo> searchTrains(){
        return employeesBusinessService.getTrains();
    }

    @GetMapping(value = "/schedules")
    public List<ScheduleDTO> searchSchedules(){
        return employeesBusinessService.getSchedules();
    }

    @GetMapping(value = "/stations")
    public List<StationDTO> searchStations(){
        return employeesBusinessService.getStations();
    }

    @GetMapping(value = "/passengers", params = "schedule_id")
    public List<UserInfo> searchPassengers(@RequestParam("schedule_id") Integer scheduleId){
        return employeesBusinessService.getPassengers(scheduleId);
    }

    @PostMapping(value = "/stations")
    @ResponseStatus(HttpStatus.CREATED)
    public StationDTO createStation(@RequestBody StationDTO station){
        return employeesBusinessService.createStation(station);
    }

    @PostMapping(value = "/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO schedule){
        return employeesBusinessService.createSchedule(schedule);
    }

    @PostMapping(value = "/trains")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainDTO createTrain(@RequestBody TrainWagons train){
        return employeesBusinessService.createTrain(train);
    }

    @GetMapping(value = "/station_names")
    public List<String> searchStationNames(){
        return employeesBusinessService.getStationNames();
    }
}