package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.SeatEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.UserEntity;
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

    public List<ScheduleEntity> findSchedules(Integer stationAId, Integer stationBId, LocalDateTime startTime, LocalDateTime endTime) {
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT s FROM ScheduleEntity s WHERE s.stationByStartStationId.stationId=:start_station and s.stationByEndStationId.stationId=:end_station and s.departureTime between :start and :end",
                ScheduleEntity.class);
        query.setParameter("start_station", stationAId);
        query.setParameter("end_station", stationBId);
        query.setParameter("start", startTime);
        query.setParameter("end", endTime);

        return query.getResultList();
    }

    public List<ScheduleEntity> findSchedulesByStationId(Integer stationId){
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT s FROM ScheduleEntity s WHERE s.stationByStartStationId.stationId = :station_id or s.stationByEndStationId.stationId = :station_id",
                ScheduleEntity.class);
        query.setParameter("station_id", stationId);

        return query.getResultList();
    }

    public List<ScheduleEntity> findSeatTicketsSchedules(Integer number, Integer wagonNumber, Integer trainNumber){
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT t.scheduleByScheduleId FROM TicketEntity t WHERE t.seat.number = :number and t.seat.wagonNumber = :wagon_number and t.seat.trainNumber = :train_number",
                ScheduleEntity.class);
        query.setParameter("number", number);
        query.setParameter("wagon_number", wagonNumber);
        query.setParameter("train_number", trainNumber);

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
        query.setParameter("train_number", trainNumber);
        query.setParameter("wagon_number", wagonNumber);
        query.setParameter("schedule_id", scheduleId);

        return query.getResultList();
    }
}