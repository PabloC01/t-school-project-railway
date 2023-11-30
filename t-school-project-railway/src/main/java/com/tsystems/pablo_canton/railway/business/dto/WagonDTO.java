package com.tsystems.pablo_canton.railway.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WagonDTO {
    private Integer wagonNumber;
    private Integer trainNumber;
    private Integer seatCount;
    private Integer seatPerRow;
    private TrainDTO train;
}
