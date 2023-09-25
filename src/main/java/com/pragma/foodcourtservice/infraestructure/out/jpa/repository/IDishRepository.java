package com.pragma.foodcourtservice.infraestructure.out.jpa.repository;

import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDishRepository extends JpaRepository<DishEntity,Long> {
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
    List<DishEntity> findByRestaurantIdAndActive(Pageable pageable, Long restaurantId, boolean active);

    //List<DishEntity> findByRestaurantIdAndActiveAndCategoryId(Pageable pageable, Long restaurantId, boolean active, Long categoryId);
}
