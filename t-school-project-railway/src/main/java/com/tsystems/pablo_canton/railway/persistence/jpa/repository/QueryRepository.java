package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class QueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String TRAIN_NUMBER = "train_number";

    public List<ScheduleEntity> findSchedules(String stationAName, String stationBName, LocalDateTime startTime, LocalDateTime endTime) {
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT s FROM ScheduleEntity s WHERE s.stationByStartStationId.name=:start_station and s.stationByEndStationId.name=:end_station and s.departureTime between :start and :end",
                ScheduleEntity.class);
        query.setParameter("start_station", stationAName);
        query.setParameter("end_station", stationBName);
        query.setParameter("start", startTime);
        query.setParameter("end", endTime);

        return query.getResultList();
    }

    public List<ScheduleEntity> findSchedulesByStationName(String stationName){
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT s FROM ScheduleEntity s WHERE s.stationByStartStationId.name = :station_name ORDER BY s.departureTime DESC",
                ScheduleEntity.class);
        query.setParameter("station_name", stationName);

        return query.getResultList();
    }

    public List<ScheduleEntity> findSeatTicketsSchedules(Integer number, Integer wagonNumber, Integer trainNumber){
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT t.scheduleByScheduleId FROM TicketEntity t WHERE t.seat.number = :number and t.seat.wagonNumber = :wagon_number and t.seat.trainNumber = :train_number",
                ScheduleEntity.class);
        query.setParameter("number", number);
        query.setParameter("wagon_number", wagonNumber);
        query.setParameter(TRAIN_NUMBER, trainNumber);

        return query.getResultList();
    }

    public List<UserEntity> findScheduleUsers(Integer scheduleId){
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT t.userByUserId FROM TicketEntity t WHERE t.scheduleByScheduleId.scheduleId = :schedule_id",
                UserEntity.class);
        query.setParameter("schedule_id", scheduleId);

        return query.getResultList();
    }

    public List<SeatEntity> findSeatsByScheduleAndWagon(Integer trainNumber, Integer wagonNumber, Integer scheduleId){
        TypedQuery<SeatEntity> query = entityManager.createQuery(
                "SELECT t.seat FROM TicketEntity t WHERE t.seat.trainNumber = :train_number and t.seat.wagonNumber = :wagon_number and t.scheduleByScheduleId.scheduleId = :schedule_id",
                SeatEntity.class);
        query.setParameter(TRAIN_NUMBER, trainNumber);
        query.setParameter("wagon_number", wagonNumber);
        query.setParameter("schedule_id", scheduleId);

        return query.getResultList();
    }

    public List<WagonEntity> findTrainWagons(Integer trainNumber){
        TypedQuery<WagonEntity> query = entityManager.createQuery(
                "SELECT t.wagonsByNumber FROM TrainEntity t WHERE t.number = :train_number",
                WagonEntity.class);
        query.setParameter(TRAIN_NUMBER, trainNumber);

        return query.getResultList();
    }

    public List<String> findStationNames() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT s.name FROM StationEntity s",
                String.class);

        return query.getResultList();
    }

    public List<TicketEntity> findClientTickets(String username) {
        TypedQuery<TicketEntity> query = entityManager.createQuery(
                "SELECT u.ticketsByUserId FROM UserEntity u WHERE u.username = :username",
                TicketEntity.class);
        query.setParameter("username", username);

        return query.getResultList();
    }

    public List<Integer> findTrainNumbers() {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT t.number FROM TrainEntity t",
                Integer.class);

        return query.getResultList();
    }
}