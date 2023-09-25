package com.pragma.foodcourtservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class DishResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private String urlImage;
}