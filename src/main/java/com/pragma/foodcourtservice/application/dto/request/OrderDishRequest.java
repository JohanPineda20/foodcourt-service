package com.pragma.foodcourtservice.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDishRequest {

    @NotNull(message = "dish_id is required")
    @Min(value = 1, message = "dish_id must be a positive number")
    private Long dishId;

    @NotNull(message = "amount cannot be empty")
    @Min(value = 1, message =  "amount must be a positive number greater than zero")
    private Long amount;

}
