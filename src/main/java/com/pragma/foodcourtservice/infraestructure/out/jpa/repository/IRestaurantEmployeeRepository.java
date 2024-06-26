package com.pragma.foodcourtservice.infraestructure.out.jpa.repository;

import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity,Long> {
    boolean existsRestaurantEmployeeByEmployeeId(Long employeeId);

    Optional<RestaurantEmployeeEntity> findByEmployeeId(Long employeeId);
}
