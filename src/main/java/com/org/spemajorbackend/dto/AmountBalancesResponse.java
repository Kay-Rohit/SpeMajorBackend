package com.org.spemajorbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountBalancesResponse {
    private Double Total;
    private Double paid;
    private Double remaining;
}
