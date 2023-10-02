package com.pragma.foodcourtservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class OrderDurationResponse {
    private Long id;
    private LocalDateTime createdAt;
    private Long durationMinutes;
}
