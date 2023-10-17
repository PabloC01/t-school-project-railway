package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

@Data
public class WagonDTO {
    private Integer wagonNumber;
    private Integer trainNumber;
    private Integer seatCount;
    private Integer seatPerRow;
    private TrainDTO train;
}
