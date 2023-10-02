package com.pragma.foodcourtservice.infraestructure.out.jpa.repository;

import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerId(Long customerId);
    List<OrderEntity> findByRestaurantId(Pageable pageable, Long restaurantId);
    List<OrderEntity> findByRestaurantIdAndStatus(Pageable pageable, Long restaurantId, StatusEnum statusEnum);
    @Query(value = "SELECT o.restaurantEmployee.id as employee, AVG(o.durationMinutes) as averageDurationMinutes " +
            "FROM OrderEntity o " +
            "WHERE o.restaurant.id = :restaurantId AND o.status = :status " +
            "GROUP BY employee " +
            "ORDER BY averageDurationMinutes ASC")
    List<Object[]> getRankingEmployee(@Param("restaurantId") Long restaurantId, @Param("status") StatusEnum status, Pageable pageable);
}
