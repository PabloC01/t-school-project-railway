package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "seat", schema = "public", catalog = "t-school-project-railway")
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntity that = (SeatEntity) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (wagonNumber != null ? !wagonNumber.equals(that.wagonNumber) : that.wagonNumber != null) return false;
        if (trainNumber != null ? !trainNumber.equals(that.trainNumber) : that.trainNumber != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (wagonNumber != null ? wagonNumber.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public WagonEntity getWagon() {
        return wagon;
    }

    public void setWagon(WagonEntity wagon) {
        this.wagon = wagon;
    }

    public Collection<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<TicketEntity> tickets) {
        this.tickets = tickets;
    }
}
