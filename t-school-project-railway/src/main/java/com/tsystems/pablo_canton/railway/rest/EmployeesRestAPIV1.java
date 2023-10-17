package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.api.employees.IEmployeesBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employee")
public class EmployeesRestAPIV1 {

    private final IEmployeesBusinessService employeesBusinessService;

    @GetMapping(value = "/trains")
    public List<TrainDTO> searchTrains(){
        return employeesBusinessService.getTrains();
    }

    @GetMapping(value = "/schedules")
    public List<ScheduleDTO> searchSchedules(){
        return employeesBusinessService.getSchedules();
    }

    @GetMapping(value = "/passengers", params = "schedule_id")
    public List<UserDTO> searchPassengers(@RequestParam("schedule_id") Integer scheduleId){
        return employeesBusinessService.getPassengers(scheduleId);
    }

    @PostMapping(value = "/stations")
    public StationDTO createStation(@RequestBody StationDTO station){
        return employeesBusinessService.createStation(station);
    }

    @PostMapping(value = "/schedules")
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO schedule){
        return employeesBusinessService.createSchedule(schedule);
    }

    @PostMapping(value = "/trains")
    public TrainDTO createTrain(@RequestBody TrainWagons train){
        return employeesBusinessService.createTrain(train);
    }
}