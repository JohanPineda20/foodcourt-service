package com.pragma.foodcourtservice.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateDishRequest {
    @DecimalMin(value = "0.1", message = "Price must be a positive number")
    private BigDecimal price;
    private String description;
}
