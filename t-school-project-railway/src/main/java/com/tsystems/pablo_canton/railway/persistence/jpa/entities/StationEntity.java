package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "station")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "schedulesByStationId", "schedulesByStationId_0"})
public class StationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "station_id")
    private Integer stationId;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "stationByStartStationId")
    private Collection<ScheduleEntity> schedulesByStationId;
    @OneToMany(mappedBy = "stationByEndStationId")
    private Collection<ScheduleEntity> schedulesByStationId_0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationEntity that = (StationEntity) o;

        if (!Objects.equals(stationId, that.stationId)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(schedulesByStationId, that.schedulesByStationId)) return false;
        return Objects.equals(schedulesByStationId_0, that.schedulesByStationId_0);
    }

    @Override
    public int hashCode() {
        int result = stationId != null ? stationId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (schedulesByStationId != null ? schedulesByStationId.hashCode() : 0);
        result = 31 * result + (schedulesByStationId_0 != null ? schedulesByStationId_0.hashCode() : 0);
        return result;
    }
}
