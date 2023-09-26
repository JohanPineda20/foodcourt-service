package com.pragma.foodcourtservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class OrderDishResponse {
    private String dishName;
    private BigDecimal dishPrice;
    private String dishCategoryName;
    private Long amount;
}
