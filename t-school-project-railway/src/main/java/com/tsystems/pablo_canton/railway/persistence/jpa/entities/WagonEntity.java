package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "wagon", schema = "public", catalog = "t-school-project-railway")
@IdClass(WagonEntityPK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "seats"})
public class WagonEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wagon_number")
    private Integer wagonNumber;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "train_number")
    private Integer trainNumber;
    @Basic
    @Column(name = "seat_count")
    private Integer seatCount;
    @Basic
    @Column(name = "seat_per_row")
    private Integer seatPerRow;
    @OneToMany(mappedBy = "wagon")
    private Collection<SeatEntity> seats;
    @ManyToOne
    @JoinColumn(name = "train_number", referencedColumnName = "number", nullable = false)
    private TrainEntity trainByNumber;

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

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Integer getSeatPerRow() {
        return seatPerRow;
    }

    public void setSeatPerRow(Integer seatPerRow) {
        this.seatPerRow = seatPerRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WagonEntity that = (WagonEntity) o;

        if (wagonNumber != null ? !wagonNumber.equals(that.wagonNumber) : that.wagonNumber != null) return false;
        if (trainNumber != null ? !trainNumber.equals(that.trainNumber) : that.trainNumber != null) return false;
        if (seatCount != null ? !seatCount.equals(that.seatCount) : that.seatCount != null) return false;
        if (seatPerRow != null ? !seatPerRow.equals(that.seatPerRow) : that.seatPerRow != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wagonNumber != null ? wagonNumber.hashCode() : 0;
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (seatCount != null ? seatCount.hashCode() : 0);
        result = 31 * result + (seatPerRow != null ? seatPerRow.hashCode() : 0);
        return result;
    }

    public Collection<SeatEntity> getSeats() {
        return seats;
    }

    public void setSeats(Collection<SeatEntity> seats) {
        this.seats = seats;
    }

    public TrainEntity getTrainByNumber() {
        return trainByNumber;
    }

    public void setTrainByNumber(TrainEntity trainByNumber) {
        this.trainByNumber = trainByNumber;
    }
}
