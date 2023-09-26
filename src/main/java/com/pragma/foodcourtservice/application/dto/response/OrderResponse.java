package com.pragma.foodcourtservice.application.dto.response;

import com.pragma.foodcourtservice.domain.model.StatusEnumModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Getter
public class OrderResponse {
    private Long id;
    private Long customerId;
    private LocalDateTime createdAt;
    private StatusEnumModel status;
    private Long employeeId;
    private String restaurantName;
    private List<OrderDishResponse> dishes;
}
