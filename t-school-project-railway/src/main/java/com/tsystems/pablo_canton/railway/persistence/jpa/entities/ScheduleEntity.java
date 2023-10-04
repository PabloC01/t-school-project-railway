package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Time;
import java.util.Collection;

@Entity
@Table(name = "schedule", schema = "public", catalog = "t-school-project-railway")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "ticketsByScheduleId"})
public class ScheduleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @Basic
    @Column(name = "departure_time")
    private Time departureTime;
    @Basic
    @Column(name = "arrival_time")
    private Time arrivalTime;
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

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (scheduleId != null ? !scheduleId.equals(that.scheduleId) : that.scheduleId != null) return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(that.arrivalTime) : that.arrivalTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduleId != null ? scheduleId.hashCode() : 0;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        return result;
    }

    public TrainEntity getTrainByNumber() {
        return trainByNumber;
    }

    public void setTrainByNumber(TrainEntity trainByNumber) {
        this.trainByNumber = trainByNumber;
    }

    public StationEntity getStationByStartStationId() {
        return stationByStartStationId;
    }

    public void setStationByStartStationId(StationEntity stationByStartStationId) {
        this.stationByStartStationId = stationByStartStationId;
    }

    public StationEntity getStationByEndStationId() {
        return stationByEndStationId;
    }

    public void setStationByEndStationId(StationEntity stationByEndStationId) {
        this.stationByEndStationId = stationByEndStationId;
    }

    public Collection<TicketEntity> getTicketsByScheduleId() {
        return ticketsByScheduleId;
    }

    public void setTicketsByScheduleId(Collection<TicketEntity> ticketsByScheduleId) {
        this.ticketsByScheduleId = ticketsByScheduleId;
    }
}
