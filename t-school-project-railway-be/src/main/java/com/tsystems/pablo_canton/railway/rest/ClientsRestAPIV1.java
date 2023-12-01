package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.api.clients.IClientsBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.*;
import com.tsystems.pablo_canton.railway.setup.security.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clients")
public class ClientsRestAPIV1 {

    private final IClientsBusinessService clientsBusinessService;

    private final TokenManager tokenManager;

    @GetMapping(value = "/search_schedule", params = {"start_station","end_station","start_time","end_time"})
    public List<ScheduleDTO> searchSchedules(@RequestParam("start_station") String startStation, @RequestParam("end_station") String endStation, @RequestParam("start_time") String startTime, @RequestParam("end_time") String endTime){
        return clientsBusinessService.getSchedules(startStation, endStation, LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
    }

    @GetMapping(value = "/schedule", params = {"station_name"})
    public List<ScheduleDTO> searchSchedulesByStationName(@RequestParam("station_name") String stationName){
        return clientsBusinessService.getSchedulesByStationName(stationName);
    }

    @GetMapping(value = "/station_names")
    public List<String> searchStationNames(){
        return clientsBusinessService.getStationNames();
    }

    @PostMapping(value = "/ticket")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO createTicket(@RequestBody BuyTicketInfo ticket){
        TicketDTO ticketDTO = new TicketDTO();

        String username = tokenManager.getUsernameByToken(ticket.getToken());
        UserDTO userDTO = clientsBusinessService.getClientByUsername(username);

        ticketDTO.setUser(userDTO);
        ticketDTO.setSeat(ticket.getSeat());
        ticketDTO.setSchedule(ticket.getSchedule());

        return clientsBusinessService.createTicket(ticketDTO);
    }

    @GetMapping(value = "/wagons", params = {"train_number", "schedule_id"})
    public List<WagonInfo> searchWagonsInfo(@RequestParam("train_number") Integer trainNumber, @RequestParam("schedule_id") Integer scheduleId){
        return clientsBusinessService.getWagonsInfo(trainNumber, scheduleId);
    }

    @GetMapping(value = "/user", params = {"token"})
    public UserInfo searchUserInfo(@RequestParam("token") String token){
        String username = tokenManager.getUsernameByToken(token);
        UserDTO userDTO = clientsBusinessService.getClientByUsername(username);

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userDTO.getUsername());
        userInfo.setName(userDTO.getName());
        userInfo.setSurname(userDTO.getSurname());
        userInfo.setBirthDate(userDTO.getBirthDate());

        return userInfo;
    }

    @GetMapping(value = "/tickets", params = {"token"})
    public List<TicketDTO> searchClientTickets(@RequestParam("token") String token){
        String username = tokenManager.getUsernameByToken(token);
        return clientsBusinessService.getClientTickets(username);
    }
}