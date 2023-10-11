package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "trains")
public class TrainEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "train_id")
    private Integer trainId;
    @Basic
    @Column(name = "number")
    private Integer number;
    @OneToMany(mappedBy = "trainByNumber", cascade = CascadeType.ALL)
    private Collection<ScheduleEntity> schedulesByNumber;
    @OneToMany(mappedBy = "trainByNumber", cascade = CascadeType.ALL)
    private Collection<WagonEntity> wagonsByNumber;
}