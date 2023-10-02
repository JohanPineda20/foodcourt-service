package com.pragma.foodcourtservice.infraestructure.out.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
