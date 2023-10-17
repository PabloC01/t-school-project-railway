package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.api.clients.IClientsBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.SeatInfo;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clients")
public class ClientsRestAPIV1 {

    private final IClientsBusinessService clientsBusinessService;

    @GetMapping(value = "/search_schedule", params = {"start_station","end_station","start_time","end_time"})
    public List<ScheduleDTO> searchSchedules(@RequestParam("start_station") Integer startStation, @RequestParam("end_station") Integer endStation, @RequestParam("start_time") String startTime, @RequestParam("end_time") String endTime){
        return clientsBusinessService.getSchedules(startStation, endStation, LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
    }

    @GetMapping(value = "/schedule", params = {"station_id"})
    public List<ScheduleDTO> searchSchedulesByStationId(@RequestParam("station_id") Integer stationId){
        return clientsBusinessService.getSchedulesByStationId(stationId);
    }

    @PostMapping(value = "/ticket")
    public TicketDTO createTicket(@RequestBody TicketDTO ticket){
        return clientsBusinessService.createTicket(ticket);
    }

    @GetMapping(value = "/empty_seats", params = {"train_number", "wagon_number", "schedule_id"})
    public List<SeatInfo> searchEmptySeats(@RequestParam("train_number") Integer trainNumber, @RequestParam("wagon_number") Integer wagonNumber, @RequestParam("schedule_id") Integer scheduleId){
        return clientsBusinessService.getEmptySeats(trainNumber, wagonNumber, scheduleId);
    }
}
