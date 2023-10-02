package com.pragma.foodcourtservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class OrderRankingResponse {
    private Long employee;
    private Double averageDurationMinutes;
}
