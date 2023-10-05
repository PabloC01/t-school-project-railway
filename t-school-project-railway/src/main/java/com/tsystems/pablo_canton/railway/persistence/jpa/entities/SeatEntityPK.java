package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SeatEntityPK implements Serializable {
    @Column(name = "number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;
    @Column(name = "wagon_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wagonNumber;
    @Column(name = "train_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntityPK that = (SeatEntityPK) o;

        if (!Objects.equals(number, that.number)) return false;
        if (!Objects.equals(wagonNumber, that.wagonNumber)) return false;
        return Objects.equals(trainNumber, that.trainNumber);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (wagonNumber != null ? wagonNumber.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        return result;
    }
}
