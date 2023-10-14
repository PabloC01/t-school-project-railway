package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Integer> {
}
