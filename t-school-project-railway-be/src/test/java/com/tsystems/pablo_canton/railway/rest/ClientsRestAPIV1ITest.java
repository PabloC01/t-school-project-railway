package com.tsystems.pablo_canton.railway.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.pablo_canton.railway.business.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ClientsRestAPIV1ITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthRestAPIV1 authRestAPIV1;

    @Test
    void testSearchSchedules() throws Exception {
        mvc.perform(get("/api/v1/clients/search_schedule?start_station=Madrid&end_station=Granada&start_time=2025-10-05T08:00:00&end_time=2025-10-05T10:00:00")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    void testSearchSchedulesByStationName() throws Exception {
        mvc.perform(get("/api/v1/clients/schedule?station_name=Madrid")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4));
    }

    @Test
    void testSearchStationNames() throws Exception {
        mvc.perform(get("/api/v1/clients/station_names")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Madrid"));
    }

    @Test
    void testCreateTicket_WhenTicketOk() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client").password("test").build());

        BuyTicketInfo dto = BuyTicketInfo.builder().token(login.getToken())
                .seat(SeatDTO.builder().number(1).wagonNumber(1).trainNumber(100).build())
                .schedule(ScheduleDTO.builder().scheduleId(1).build()).build();

        mvc.perform(post("/api/v1/clients/ticket")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateTicket_WhenSeatIsBusy() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client").password("test").build());

        BuyTicketInfo dto = BuyTicketInfo.builder().token(login.getToken())
                .seat(SeatDTO.builder().number(1).wagonNumber(1).trainNumber(100).build())
                .schedule(ScheduleDTO.builder().scheduleId(4).build()).build();

        mvc.perform(post("/api/v1/clients/ticket")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("seat not free"));
    }

    @Test
    void testCreateTicket_WhenUserAlreadyHaveTicket() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client").password("test").build());

        BuyTicketInfo dto = BuyTicketInfo.builder().token(login.getToken())
                .seat(SeatDTO.builder().number(2).wagonNumber(1).trainNumber(100).build())
                .schedule(ScheduleDTO.builder().scheduleId(3).build()).build();

        mvc.perform(post("/api/v1/clients/ticket")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("already have ticket"));
    }

    @Test
    void testCreateTicket_WhenUserIsNotClient() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.employee").password("test").build());

        BuyTicketInfo dto = BuyTicketInfo.builder().token(login.getToken())
                .seat(SeatDTO.builder().number(1).wagonNumber(1).trainNumber(100).build())
                .schedule(ScheduleDTO.builder().scheduleId(1).build()).build();

        mvc.perform(post("/api/v1/clients/ticket")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("user not client"));
    }

    @Test
    void testCreateTicket_WhenTooLateBeforeDeparture() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client").password("test").build());

        BuyTicketInfo dto = BuyTicketInfo.builder().token(login.getToken())
                .seat(SeatDTO.builder().number(1).wagonNumber(1).trainNumber(100).build())
                .schedule(ScheduleDTO.builder().scheduleId(2).build()).build();

        mvc.perform(post("/api/v1/clients/ticket")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("too late"));
    }

    @Test
    void testSearchWagonsInfo() throws Exception {
        mvc.perform(get("/api/v1/clients/wagons?train_number=100&schedule_id=1")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testSearchUserInfo() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client").password("test").build());

        mvc.perform(get("/api/v1/clients/user?token=" + login.getToken())
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test.client"));
    }

    @Test
    void testSearchClientTickets() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client2").password("test").build());

        mvc.perform(get("/api/v1/clients/tickets?token=" + login.getToken())
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}