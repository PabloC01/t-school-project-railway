package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "seats")
@IdClass(SeatEntityPK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tickets"})
public class SeatEntity {
    @Id
    @Column(name = "number")
    private Integer number;
    @Id
    @Column(name = "wagon_number")
    private Integer wagonNumber;
    @Id
    @Column(name = "train_number")
    private Integer trainNumber;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "wagon_number", referencedColumnName = "wagon_number", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false, insertable = false, updatable = false)
    private WagonEntity wagon;
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private Collection<TicketEntity> tickets;

    public String toString() {
        return "SeatEntity(number=" + this.getNumber() + ", wagonNumber=" + this.getWagonNumber() + ", trainNumber=" + this.getTrainNumber() + ", description=" + this.getDescription() + ", wagon=" + this.getWagon().toString() + ")";
    }
}
