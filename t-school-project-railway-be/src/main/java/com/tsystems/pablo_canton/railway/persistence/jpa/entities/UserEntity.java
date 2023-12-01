package com.tsystems.pablo_canton.railway.persistence.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "ticketsByUserId"})
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "username", unique = true)
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "role")
    private String role;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Collection<TicketEntity> ticketsByUserId;

    public String toString() {
        return "UserEntity(userId=" + this.getUserId() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ", role=" + this.getRole() + ", name=" + this.getName() + ", surname=" + this.getSurname() + ", birthDate=" + this.getBirthDate() + ")";
    }
}
