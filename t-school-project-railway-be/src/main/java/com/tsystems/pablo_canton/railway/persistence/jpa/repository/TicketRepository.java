package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
