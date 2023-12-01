package com.tsystems.pablo_canton.railway.persistence.jpa.repository;

import com.tsystems.pablo_canton.railway.persistence.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsUserEntityByUsername(String username);
}
