package com.tsystems.pablo_canton.railway.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainWagons {
    private TrainDTO train;
    private List<WagonDTO> wagons;
}
