package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "wagons")
@IdClass(WagonEntityPK.class)
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
    @OneToMany(mappedBy = "wagon", cascade = CascadeType.ALL)
    private Collection<SeatEntity> seats;
    @ManyToOne
    @JoinColumn(name = "train_number", referencedColumnName = "number", nullable = false)
    private TrainEntity trainByNumber;
}
