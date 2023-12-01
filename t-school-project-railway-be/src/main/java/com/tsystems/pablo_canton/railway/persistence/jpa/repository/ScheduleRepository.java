package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
}
