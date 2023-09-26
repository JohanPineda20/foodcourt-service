package com.pragma.foodcourtservice.infraestructure.out.jpa.repository;

import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.StatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerId(Long customerId);
    List<OrderEntity> findByRestaurantId(Pageable pageable, Long restaurantId);
    List<OrderEntity> findByRestaurantIdAndStatus(Pageable pageable, Long restaurantId, StatusEnum statusEnum);
}
