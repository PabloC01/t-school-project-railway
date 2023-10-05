package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "ticketsByScheduleId"})
public class ScheduleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @Basic
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Basic
    @Column(name = "arrival_time")
    private LocalDateTime  arrivalTime;
    @ManyToOne
    @JoinColumn(name = "train_number", referencedColumnName = "number", nullable = false)
    private TrainEntity trainByNumber;
    @ManyToOne
    @JoinColumn(name = "start_station_id", referencedColumnName = "station_id", nullable = false)
    private StationEntity stationByStartStationId;
    @ManyToOne
    @JoinColumn(name = "end_station_id", referencedColumnName = "station_id")
    private StationEntity stationByEndStationId;
    @OneToMany(mappedBy = "scheduleByScheduleId")
    private Collection<TicketEntity> ticketsByScheduleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (!Objects.equals(scheduleId, that.scheduleId)) return false;
        if (!Objects.equals(departureTime, that.departureTime))
            return false;
        if (!Objects.equals(arrivalTime, that.arrivalTime)) return false;
        if (!Objects.equals(trainByNumber, that.trainByNumber)) return false;
        if (!Objects.equals(stationByStartStationId, that.stationByStartStationId)) return false;
        if (!Objects.equals(stationByEndStationId, that.stationByEndStationId)) return false;
        return Objects.equals(ticketsByScheduleId, that.ticketsByScheduleId);
    }

    @Override
    public int hashCode() {
        int result = scheduleId != null ? scheduleId.hashCode() : 0;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (trainByNumber != null ? trainByNumber.hashCode() : 0);
        result = 31 * result + (stationByStartStationId != null ? stationByStartStationId.hashCode() : 0);
        result = 31 * result + (stationByEndStationId != null ? stationByEndStationId.hashCode() : 0);
        result = 31 * result + (ticketsByScheduleId != null ? ticketsByScheduleId.hashCode() : 0);
        return result;
    }
}
