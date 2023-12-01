package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

@Data
public class TrainInfo {
    private Integer trainId;
    private Integer number;
    private Integer wagonCount;
    private Integer seatCount;
}