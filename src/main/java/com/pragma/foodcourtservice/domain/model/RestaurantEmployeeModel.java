package com.pragma.foodcourtservice.domain.model;

public class RestaurantEmployeeModel {

    private Long id;
    private Long employeeId;
    private RestaurantModel restaurant;
    public RestaurantEmployeeModel(){}
    public RestaurantEmployeeModel(Long id, Long employeeId, RestaurantModel restaurant) {
        this.id = id;
        this.employeeId = employeeId;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
