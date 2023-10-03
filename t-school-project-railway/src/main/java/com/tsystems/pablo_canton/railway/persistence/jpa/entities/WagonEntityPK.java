package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class WagonEntityPK implements Serializable {
    @Column(name = "wagon_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wagonNumber;
    @Column(name = "train_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainNumber;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WagonEntityPK that = (WagonEntityPK) o;

        if (wagonNumber != null ? !wagonNumber.equals(that.wagonNumber) : that.wagonNumber != null) return false;
        if (trainNumber != null ? !trainNumber.equals(that.trainNumber) : that.trainNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wagonNumber != null ? wagonNumber.hashCode() : 0;
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        return result;
    }
}
