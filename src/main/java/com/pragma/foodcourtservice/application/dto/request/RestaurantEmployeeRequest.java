package com.pragma.foodcourtservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantEmployeeRequest {
    private Long ownerId;
    private Long employeeId;
}
