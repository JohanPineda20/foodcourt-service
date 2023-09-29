package com.pragma.foodcourtservice.infraestructure.out.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrackingFeignDto {
    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime datetime;
    private String statusPrevious;
    private String status;
    private Long employeeId;
    private String employeeEmail;
}
