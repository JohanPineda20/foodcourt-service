package com.pragma.foodcourtservice.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String description;
}
