package com.pragma.foodcourtservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {

    @NotNull(message = "restaurant_id is required")
    @Min(value = 1, message = "restaurant_id must be a positive number")
    private Long restaurantId;

    @NotEmpty(message = "list of dishes cannot be empty")
    @Valid
    private List<OrderDishRequest> dishes;
}
