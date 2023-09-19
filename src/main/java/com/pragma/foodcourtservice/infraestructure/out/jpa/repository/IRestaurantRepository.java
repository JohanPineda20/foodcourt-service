package com.pragma.foodcourtservice.infraestructure.out.jpa.repository;

import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity,Long> {

    boolean existsByName(String name);

    boolean existsByNit(String nit);

    Optional<RestaurantEntity> findByOwnerId(Long ownerId);
}
