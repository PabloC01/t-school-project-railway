package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class SeatEntityPK implements Serializable {
    @Column(name = "number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;
    @Column(name = "wagon_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wagonNumber;
    @Column(name = "train_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainNumber;
}
