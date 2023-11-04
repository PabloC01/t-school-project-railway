package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Integer userId;
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private LocalDate birthDate;
}
