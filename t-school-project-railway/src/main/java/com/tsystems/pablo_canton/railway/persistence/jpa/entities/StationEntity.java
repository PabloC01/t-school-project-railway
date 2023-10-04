package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "station", schema = "public", catalog = "t-school-project-railway")
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

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationEntity that = (StationEntity) o;

        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stationId != null ? stationId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Collection<ScheduleEntity> getSchedulesByStationId() {
        return schedulesByStationId;
    }

    public void setSchedulesByStationId(Collection<ScheduleEntity> schedulesByStationId) {
        this.schedulesByStationId = schedulesByStationId;
    }

    public Collection<ScheduleEntity> getSchedulesByStationId_0() {
        return schedulesByStationId_0;
    }

    public void setSchedulesByStationId_0(Collection<ScheduleEntity> schedulesByStationId_0) {
        this.schedulesByStationId_0 = schedulesByStationId_0;
    }
}
