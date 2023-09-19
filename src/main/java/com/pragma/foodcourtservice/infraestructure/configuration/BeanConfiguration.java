package com.pragma.foodcourtservice.infraestructure.configuration;

import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IUserFeignClientPort;
import com.pragma.foodcourtservice.domain.usecase.RestaurantUseCase;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.IUserFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter.UserFeignClientAdapter;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper.IUserFeignDtoMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.RestaurantMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IRestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUserFeignClientPort userFeignClientPort(IUserFeignClient userFeignClient, IUserFeignDtoMapper userFeignDtoMapper){
        return new UserFeignClientAdapter(userFeignClient, userFeignDtoMapper);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(IRestaurantRepository restaurantRepository, IRestaurantEntityMapper restaurantEntityMapper){
        return new RestaurantMysqlAdapter(restaurantRepository,restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClientPort){
        return new RestaurantUseCase(restaurantPersistencePort, userFeignClientPort);
    }

}
