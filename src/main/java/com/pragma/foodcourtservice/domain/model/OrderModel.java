package com.pragma.foodcourtservice.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class OrderModel {
    private Long id;
    private Long customerId;
    private LocalDateTime createdAt;
    private StatusEnumModel status;
    private RestaurantEmployeeModel restaurantEmployee;
    private RestaurantModel restaurant;
    private List<OrderDishModel> dishes;
    private Long durationMinutes;

    public OrderModel() {
    }
    public OrderModel(Long id, Long customerId, LocalDateTime createdAt, StatusEnumModel status, RestaurantEmployeeModel restaurantEmployee, RestaurantModel restaurant, List<OrderDishModel> dishes, Long durationMinutes) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.status = status;
        this.restaurantEmployee = restaurantEmployee;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.durationMinutes = durationMinutes;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public StatusEnumModel getStatus() {
        return status;
    }

    public void setStatus(StatusEnumModel status) {
        this.status = status;
    }

    public RestaurantEmployeeModel getRestaurantEmployee() {
        return restaurantEmployee;
    }

    public void setRestaurantEmployee(RestaurantEmployeeModel restaurantEmployee) {
        this.restaurantEmployee = restaurantEmployee;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderDishModel> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishModel> dishes) {
        this.dishes = dishes;
    }
    public Long getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
