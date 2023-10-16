package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "stations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "schedulesByStationId", "schedulesByStationId_0"})
public class StationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "station_id")
    private Integer stationId;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "stationByStartStationId", cascade = CascadeType.ALL)
    private Collection<ScheduleEntity> schedulesByStartStationId;
    @OneToMany(mappedBy = "stationByEndStationId", cascade = CascadeType.ALL)
    private Collection<ScheduleEntity> schedulesByEndStationId;

    public String toString() {
        return "StationEntity(stationId=" + this.getStationId() + ", name=" + this.getName() + ")";
    }
}
