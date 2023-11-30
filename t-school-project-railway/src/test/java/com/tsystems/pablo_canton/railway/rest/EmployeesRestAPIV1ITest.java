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

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class EmployeesRestAPIV1ITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSearchTrains() throws Exception {
        mvc.perform(get("/api/v1/employee/trains")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(100));
    }

    @Test
    void testSearchSchedules() throws Exception {
        mvc.perform(get("/api/v1/employee/schedules")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].train.number").value(100));
    }

    @Test
    void testSearchStations() throws Exception {
        mvc.perform(get("/api/v1/employee/stations")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Madrid"));
    }

    @Test
    void testSearchPassengers() throws Exception {
        mvc.perform(get("/api/v1/employee/passengers?schedule_id=3")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].username").value("test.client"));
    }

    @Test
    void testCreateStation_WhenStationNameAvailable() throws Exception {
        StationDTO dto = StationDTO.builder().name("Almería").build();

        mvc.perform(post("/api/v1/employee/stations")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Almería"));
    }

    @Test
    void testCreateStation_WhenStationNameNotAvailable() throws Exception {
        StationDTO dto = StationDTO.builder().name("Madrid").build();

        mvc.perform(post("/api/v1/employee/stations")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("station name exists"));
    }

    @Test
    void testCreateSchedule_WhenScheduleOk() throws Exception {
        ScheduleDTO dto = ScheduleDTO.builder().departureTime(LocalDateTime.parse("2024-11-04T08:30:00"))
                .arrivalTime(LocalDateTime.parse("2024-11-04T12:00:00"))
                .train(TrainDTO.builder().number(100).build())
                .startStation(StationDTO.builder().name("Madrid").build())
                .endStation(StationDTO.builder().name("Granada").build()).build();

        mvc.perform(post("/api/v1/employee/schedules")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.train.number").value(100));
    }

    @Test
    void testCreateSchedule_WhenTrainDontExists() throws Exception {
        ScheduleDTO dto = ScheduleDTO.builder().departureTime(LocalDateTime.parse("2024-11-04T08:30:00"))
                .arrivalTime(LocalDateTime.parse("2024-11-04T12:00:00"))
                .train(TrainDTO.builder().number(1).build())
                .startStation(StationDTO.builder().name("Madrid").build())
                .endStation(StationDTO.builder().name("Granada").build()).build();

        mvc.perform(post("/api/v1/employee/schedules")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Train not found 1"));
    }

    @Test
    void testCreateSchedule_WhenStationDontExists() throws Exception {
        ScheduleDTO dto = ScheduleDTO.builder().departureTime(LocalDateTime.parse("2024-11-04T08:30:00"))
                .arrivalTime(LocalDateTime.parse("2024-11-04T12:00:00"))
                .train(TrainDTO.builder().number(100).build())
                .startStation(StationDTO.builder().name("Wrong").build())
                .endStation(StationDTO.builder().name("Granada").build()).build();

        mvc.perform(post("/api/v1/employee/schedules")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Station not found Wrong"));
    }

    @Test
    void testCreateTrain_WhenTrainOk() throws Exception {
        TrainWagons dto = TrainWagons.builder().train(TrainDTO.builder().number(101).build())
                .wagons(Collections.singletonList(WagonDTO.builder().seatCount(10).seatPerRow(2).build())).build();

        mvc.perform(post("/api/v1/employee/trains")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value(101));
    }

    @Test
    void testCreateTrain_WhenTrainNumberNotAvailable() throws Exception {
        TrainWagons dto = TrainWagons.builder().train(TrainDTO.builder().number(100).build())
                .wagons(Collections.singletonList(WagonDTO.builder().seatCount(10).seatPerRow(2).build())).build();

        mvc.perform(post("/api/v1/employee/trains")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("train number exists"));
    }

    @Test
    void testSearchStationNames() throws Exception {
        mvc.perform(get("/api/v1/employee/station_names")
                        .contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Madrid"));
    }
}