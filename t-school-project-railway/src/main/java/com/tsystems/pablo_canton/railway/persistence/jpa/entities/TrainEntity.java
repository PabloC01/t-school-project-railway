package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "train", schema = "public", catalog = "t-school-project-railway")
public class TrainEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "train_id")
    private Integer trainId;
    @Basic
    @Column(name = "number")
    private Integer number;
    @OneToMany(mappedBy = "trainByNumber")
    private Collection<ScheduleEntity> schedulesByNumber;
    @OneToMany(mappedBy = "trainByNumber")
    private Collection<WagonEntity> wagonsByNumber;

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainEntity that = (TrainEntity) o;

        if (trainId != null ? !trainId.equals(that.trainId) : that.trainId != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainId != null ? trainId.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    public Collection<ScheduleEntity> getSchedulesByNumber() {
        return schedulesByNumber;
    }

    public void setSchedulesByNumber(Collection<ScheduleEntity> schedulesByNumber) {
        this.schedulesByNumber = schedulesByNumber;
    }

    public Collection<WagonEntity> getWagonsByNumber() {
        return wagonsByNumber;
    }

    public void setWagonsByNumber(Collection<WagonEntity> wagonsByNumber) {
        this.wagonsByNumber = wagonsByNumber;
    }
}
