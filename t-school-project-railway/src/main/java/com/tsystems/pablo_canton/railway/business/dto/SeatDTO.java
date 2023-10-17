package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

@Data
public class SeatDTO {
    private Integer number;
    private Integer wagonNumber;
    private Integer trainNumber;
    private String description;
    private WagonDTO wagon;
}
