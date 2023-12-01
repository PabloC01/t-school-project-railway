package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.WagonEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.WagonEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WagonRepository extends JpaRepository<WagonEntity, WagonEntityPK> {
}
