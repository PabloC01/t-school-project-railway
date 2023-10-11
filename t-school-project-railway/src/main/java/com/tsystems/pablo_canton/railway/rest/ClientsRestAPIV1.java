package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.api.clients.IClientsBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
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
    public List<ScheduleDTO> searchSchedules(@RequestParam Integer start_station, @RequestParam Integer end_station, @RequestParam String start_time, @RequestParam String end_time){
        return clientsBusinessService.getSchedules(start_station, end_station, LocalDateTime.parse(start_time), LocalDateTime.parse(end_time));
    }

    @GetMapping(value = "/schedule", params = {"station_id"})
    public List<ScheduleDTO> searchSchedulesByStationId(@RequestParam Integer station_id){
        return clientsBusinessService.getSchedulesByStationId(station_id);
    }

    @PostMapping(value = "ticket")
    public TicketDTO createTicket(@RequestBody TicketDTO ticket){
        return clientsBusinessService.createTicket(ticket);
    }
}
