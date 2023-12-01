package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class WagonEntityPK implements Serializable {
    @Column(name = "wagon_number")
    @Id
    private Integer wagonNumber;
    @Column(name = "train_number")
    @Id
    private Integer trainNumber;
}
