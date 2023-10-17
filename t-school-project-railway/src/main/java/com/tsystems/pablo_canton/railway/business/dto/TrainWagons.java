package com.tsystems.pablo_canton.railway.business.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainWagons {
    private TrainDTO train;
    private List<WagonDTO> wagons;
}
