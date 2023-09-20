package com.pragma.foodcourtservice.infraestructure.configuration;

import com.pragma.foodcourtservice.domain.api.ICategoryServicePort;
import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import com.pragma.foodcourtservice.domain.spi.ICategoryPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IUserFeignClientPort;
import com.pragma.foodcourtservice.domain.usecase.CategoryUseCase;
import com.pragma.foodcourtservice.domain.usecase.DishUseCase;
import com.pragma.foodcourtservice.domain.usecase.RestaurantUseCase;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.IUserFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter.UserFeignClientAdapter;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper.IUserFeignDtoMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.CategoryMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.DishMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.RestaurantMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.ICategoryRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IDishRepository;
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
    public IDishPersistencePort dishPersistencePort(IDishRepository dishRepository, IDishEntityMapper dishEntityMapper){
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(IRestaurantRepository restaurantRepository, IRestaurantEntityMapper restaurantEntityMapper){
        return new RestaurantMysqlAdapter(restaurantRepository,restaurantEntityMapper);
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper){
        return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }


    @Bean
    public IRestaurantServicePort restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClientPort){
        return new RestaurantUseCase(restaurantPersistencePort, userFeignClientPort);
    }
    @Bean
    public ICategoryServicePort categoryServicePort(ICategoryPersistencePort categoryPersistencePort){
        return new CategoryUseCase(categoryPersistencePort);
    }
    @Bean
    public IDishServicePort dishServicePort(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort){
        return new DishUseCase(dishPersistencePort, categoryPersistencePort);
    }

}
