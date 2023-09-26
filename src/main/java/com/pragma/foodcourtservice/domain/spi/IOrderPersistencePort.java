package com.pragma.foodcourtservice.domain.spi;

import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;

import java.util.List;

public interface IOrderPersistencePort {
    List<OrderModel> findByCustomerId(Long customerId);
    OrderModel save(OrderModel orderModel);
    void saveOrderDish(List<OrderDishModel> dishes);
    List<OrderModel> getAllOrdersByRestaurant(Integer page, Integer size, Long restaurantId);
    List<OrderDishModel> getAllDishesByOrderId(Long orderId);
}
