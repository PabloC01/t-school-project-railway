package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
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

    public List<ScheduleEntity> findSchedules(Integer station_a_id, Integer station_b_id, LocalDateTime start_time, LocalDateTime end_time) {
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT s FROM ScheduleEntity s WHERE s.stationByStartStationId.stationId=:start_station and s.stationByEndStationId.stationId=:end_station and s.departureTime between :start and :end",
                ScheduleEntity.class);
        query.setParameter("start_station", station_a_id);
        query.setParameter("end_station", station_b_id);
        query.setParameter("start", start_time);
        query.setParameter("end", end_time);

        return query.getResultList();
    }

    public List<ScheduleEntity> findSchedulesByStationId(Integer station_id){
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT s FROM ScheduleEntity s WHERE s.stationByStartStationId.stationId = :station_id or s.stationByEndStationId.stationId = :station_id",
                ScheduleEntity.class);
        query.setParameter("station_id", station_id);

        return query.getResultList();
    }

    public List<ScheduleEntity> findSeatTicketsSchedules(Integer number, Integer wagon_number, Integer train_number){
        TypedQuery<ScheduleEntity> query = entityManager.createQuery(
                "SELECT t.scheduleByScheduleId FROM TicketEntity t WHERE t.seat.number = :number and t.seat.wagonNumber = :wagon_number and t.seat.trainNumber = :train_number",
                ScheduleEntity.class);
        query.setParameter("number", number);
        query.setParameter("wagon_number", wagon_number);
        query.setParameter("train_number", train_number);

        return query.getResultList();
    }

    public List<UserEntity> findScheduleUsers(Integer schedule_id){
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT t.userByUserId FROM TicketEntity t WHERE t.scheduleByScheduleId.scheduleId = :schedule_id",
                UserEntity.class);
        query.setParameter("schedule_id", schedule_id);

        return query.getResultList();
    }
}