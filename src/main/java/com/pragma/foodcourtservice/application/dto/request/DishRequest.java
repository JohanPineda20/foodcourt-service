package com.pragma.foodcourtservice.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DishRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Price cannot be empty")
    @DecimalMin(value = "0.1", message = "Price must be a positive number")
    private BigDecimal price;
    private String description;
    private String urlImage;
    @NotNull(message = "Category_id is required")
    @DecimalMin(value = "1", message = "Category_id must be a positive number")
    private Long categoryId;

}
