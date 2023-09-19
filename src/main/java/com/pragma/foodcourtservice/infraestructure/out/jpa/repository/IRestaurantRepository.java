package com.pragma.foodcourtservice.infraestructure.out.jpa.repository;

import com.pragma.foodcourtservice.infraestructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity,Long> {

}
