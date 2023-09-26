package com.pragma.foodcourtservice.domain.model;

public class OrderDishModel {

    private Long id;
    private DishModel dish;
    private OrderModel order;
    private Long amount;

    public OrderDishModel() {
    }
    public OrderDishModel(Long id, DishModel dish, OrderModel order, Long amount) {
        this.id = id;
        this.dish = dish;
        this.order = order;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DishModel getDish() {
        return dish;
    }

    public void setDish(DishModel dish) {
        this.dish = dish;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
