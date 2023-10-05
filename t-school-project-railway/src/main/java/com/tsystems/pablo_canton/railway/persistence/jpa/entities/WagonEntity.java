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
@Table(name = "wagon")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WagonEntity that = (WagonEntity) o;

        if (!Objects.equals(wagonNumber, that.wagonNumber)) return false;
        if (!Objects.equals(trainNumber, that.trainNumber)) return false;
        if (!Objects.equals(seatCount, that.seatCount)) return false;
        if (!Objects.equals(seatPerRow, that.seatPerRow)) return false;
        if (!Objects.equals(seats, that.seats)) return false;
        return Objects.equals(trainByNumber, that.trainByNumber);
    }

    @Override
    public int hashCode() {
        int result = wagonNumber != null ? wagonNumber.hashCode() : 0;
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (seatCount != null ? seatCount.hashCode() : 0);
        result = 31 * result + (seatPerRow != null ? seatPerRow.hashCode() : 0);
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainByNumber.hashCode() : 0);
        return result;
    }
}
