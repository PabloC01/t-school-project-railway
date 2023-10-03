package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket", schema = "public", catalog = "t-school-project-railway")
public class TicketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ticket_id")
    private Integer ticketId;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "seat_number")
    private Integer seatNumber;
    @Basic
    @Column(name = "wagon_number")
    private Integer wagonNumber;
    @Basic
    @Column(name = "train_number")
    private Integer trainNumber;
    @Basic
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userByUserId;
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "seat_number", referencedColumnName = "number", nullable = false), @JoinColumn(name = "wagon_number", referencedColumnName = "wagon_number", nullable = false), @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false)})
    private SeatEntity seat;
    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false)
    private ScheduleEntity scheduleByScheduleId;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(Integer wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (ticketId != null ? !ticketId.equals(that.ticketId) : that.ticketId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (seatNumber != null ? !seatNumber.equals(that.seatNumber) : that.seatNumber != null) return false;
        if (wagonNumber != null ? !wagonNumber.equals(that.wagonNumber) : that.wagonNumber != null) return false;
        if (trainNumber != null ? !trainNumber.equals(that.trainNumber) : that.trainNumber != null) return false;
        if (scheduleId != null ? !scheduleId.equals(that.scheduleId) : that.scheduleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticketId != null ? ticketId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (seatNumber != null ? seatNumber.hashCode() : 0);
        result = 31 * result + (wagonNumber != null ? wagonNumber.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (scheduleId != null ? scheduleId.hashCode() : 0);
        return result;
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    public SeatEntity getSeat() {
        return seat;
    }

    public void setSeat(SeatEntity seat) {
        this.seat = seat;
    }

    public ScheduleEntity getScheduleByScheduleId() {
        return scheduleByScheduleId;
    }

    public void setScheduleByScheduleId(ScheduleEntity scheduleByScheduleId) {
        this.scheduleByScheduleId = scheduleByScheduleId;
    }
}
