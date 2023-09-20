package com.pragma.foodcourtservice.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantRequest {
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[0-9a-zA-Z]+$", message = "Name can contain numbers with letters but not only numbers")
    private String name;

    @NotBlank(message = "Nit cannot be empty")
    @Pattern(regexp = "\\d+", message = "Nit must be numerical")
    private String nit;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{1,12}$", message = "phone is invalid")
    private String phone;

    @NotBlank(message = "url_logo cannot be empty")
    private String urlLogo;

    @NotNull(message = "owner_id is required")
    @Min(value = 1, message = "The owner_id must be a positive number")
    private Long ownerId;

}
