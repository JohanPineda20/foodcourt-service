package com.pragma.foodcourtservice.infraestructure.input.rest;

import com.pragma.foodcourtservice.application.dto.request.RestaurantRequest;
import com.pragma.foodcourtservice.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Restaurant Controller")
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantHandler restaurantHandler;
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody RestaurantRequest restaurantRequest){
        restaurantHandler.save(restaurantRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
