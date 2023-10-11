package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "stations")
public class StationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "station_id")
    private Integer stationId;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "stationByStartStationId", cascade = CascadeType.ALL)
    private Collection<ScheduleEntity> schedulesByStationId;
    @OneToMany(mappedBy = "stationByEndStationId", cascade = CascadeType.ALL)
    private Collection<ScheduleEntity> schedulesByStationId_0;
}
