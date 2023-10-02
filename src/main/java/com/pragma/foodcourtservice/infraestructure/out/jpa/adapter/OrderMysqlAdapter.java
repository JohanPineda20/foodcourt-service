package com.pragma.foodcourtservice.infraestructure.out.jpa.adapter;

import com.pragma.foodcourtservice.domain.model.OrderDishModel;
import com.pragma.foodcourtservice.domain.model.OrderModel;
import com.pragma.foodcourtservice.domain.model.StatusEnumModel;
import com.pragma.foodcourtservice.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderDishEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public List<OrderModel> findByCustomerId(Long customerId) {
        return orderEntityMapper.mapToOrderModelList(orderRepository.findByCustomerId(customerId));
    }

    @Override
    public OrderModel save(OrderModel orderModel) {
        return orderEntityMapper.mapToOrderModel(orderRepository.save(orderEntityMapper.mapToOrderEntity(orderModel)));
    }

    @Override
    public void saveOrderDish(List<OrderDishModel> dishes) {
        List<OrderDishEntity> orderDishEntityList = orderDishEntityMapper.mapToOrderDishEntityList(dishes);
        orderDishRepository.saveAll(orderDishEntityList);
    }

    @Override
    public List<OrderModel> getAllOrdersByRestaurant(Integer page, Integer size, Long restaurantId) {
        Pageable pageable = PageRequest.of(page, size);
        return orderEntityMapper.mapToOrderModelList(orderRepository.findByRestaurantId(pageable, restaurantId));
    }

    @Override
    public List<OrderDishModel> getAllDishesByOrderId(Long orderId) {
        return orderDishEntityMapper.mapToOrderDishModelList(orderDishRepository.findByOrderId(orderId));
    }

    @Override
    public List<OrderModel> getAllOrdersByRestaurantAndStatus(Integer page, Integer size, Long restaurantId, StatusEnumModel statusEnumModel) {
        Pageable pageable = PageRequest.of(page, size);
        return orderEntityMapper.mapToOrderModelList(orderRepository.findByRestaurantIdAndStatus(pageable, restaurantId, orderEntityMapper.mapToStatusEnum(statusEnumModel)));
    }

    @Override
    public OrderModel findById(Long id) {
        return orderEntityMapper.mapToOrderModel(orderRepository.findById(id).orElse(null));
    }

    @Override
    public List<Object[]> getRankingEmployee(Integer page, Integer size, Long restaurantId, StatusEnumModel statusEnumModel) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.getRankingEmployee(restaurantId, orderEntityMapper.mapToStatusEnum(statusEnumModel), pageable);
    }
}
