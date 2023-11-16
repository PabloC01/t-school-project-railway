package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "schedules")
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
    @JoinColumn(name = "end_station_id", referencedColumnName = "station_id", nullable = false)
    private StationEntity stationByEndStationId;
    @OneToMany(mappedBy = "scheduleByScheduleId", cascade = CascadeType.ALL)
    private Collection<TicketEntity> ticketsByScheduleId;

    @Override
    public String toString() {
        return "ScheduleEntity(scheduleId=" + this.getScheduleId() + ", departureTime=" + this.getDepartureTime() + ", arrivalTime=" + this.getArrivalTime() + ", trainByNumber=" + this.getTrainByNumber().toString() + ", stationByStartStationId=" + this.getStationByStartStationId().toString() + ", stationByEndStationId=" + this.getStationByEndStationId().toString() + ")";
    }
}
