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
@Table(name = "seat")
@IdClass(SeatEntityPK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tickets"})
public class SeatEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "number")
    private Integer number;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wagon_number")
    private Integer wagonNumber;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "train_number")
    private Integer trainNumber;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "wagon_number", referencedColumnName = "wagon_number", nullable = false), @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false)})
    private WagonEntity wagon;
    @OneToMany(mappedBy = "seat")
    private Collection<TicketEntity> tickets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntity that = (SeatEntity) o;

        if (!Objects.equals(number, that.number)) return false;
        if (!Objects.equals(wagonNumber, that.wagonNumber)) return false;
        if (!Objects.equals(trainNumber, that.trainNumber)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(wagon, that.wagon)) return false;
        return Objects.equals(tickets, that.tickets);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (wagonNumber != null ? wagonNumber.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (wagon != null ? wagon.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        return result;
    }
}
