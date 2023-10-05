package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "ticket")
public class TicketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ticket_id")
    private Integer ticketId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userByUserId;
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "seat_number", referencedColumnName = "number", nullable = false), @JoinColumn(name = "wagon_number", referencedColumnName = "wagon_number", nullable = false), @JoinColumn(name = "train_number", referencedColumnName = "train_number", nullable = false)})
    private SeatEntity seat;
    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false)
    private ScheduleEntity scheduleByScheduleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (!Objects.equals(ticketId, that.ticketId)) return false;
        if (!Objects.equals(userByUserId, that.userByUserId)) return false;
        if (!Objects.equals(seat, that.seat)) return false;
        return Objects.equals(scheduleByScheduleId, that.scheduleByScheduleId);
    }

    @Override
    public int hashCode() {
        int result = ticketId != null ? ticketId.hashCode() : 0;
        result = 31 * result + (userByUserId != null ? userByUserId.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (scheduleByScheduleId != null ? scheduleByScheduleId.hashCode() : 0);

        return result;
    }
}
