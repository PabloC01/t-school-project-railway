package com.tsystems.pablo_canton.railway.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInfo {
    private String username;
    private String name;
    private String surname;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;
}
