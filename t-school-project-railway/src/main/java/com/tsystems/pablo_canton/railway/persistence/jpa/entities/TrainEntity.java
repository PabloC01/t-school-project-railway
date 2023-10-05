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
@Table(name = "train")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "schedulesByNumber", "wagonsByNumber"})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainEntity that = (TrainEntity) o;

        if (!Objects.equals(trainId, that.trainId)) return false;
        if (!Objects.equals(number, that.number)) return false;
        if (!Objects.equals(schedulesByNumber, that.schedulesByNumber)) return false;
        return Objects.equals(wagonsByNumber, that.wagonsByNumber);
    }

    @Override
    public int hashCode() {
        int result = trainId != null ? trainId.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (schedulesByNumber != null ? schedulesByNumber.hashCode() : 0);
        result = 31 * result + (wagonsByNumber != null ? wagonsByNumber.hashCode() : 0);
        return result;
    }
}
