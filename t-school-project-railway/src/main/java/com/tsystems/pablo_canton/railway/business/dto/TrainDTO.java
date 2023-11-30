package com.tsystems.pablo_canton.railway.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainDTO {
    private Integer trainId;
    private Integer number;
}
