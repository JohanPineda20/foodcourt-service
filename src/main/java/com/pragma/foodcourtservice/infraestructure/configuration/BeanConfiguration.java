package com.pragma.foodcourtservice.infraestructure.configuration;

import com.pragma.foodcourtservice.domain.api.ICategoryServicePort;
import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import com.pragma.foodcourtservice.domain.spi.*;
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
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.ICategoryRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IDishRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IRestaurantEmployeeRepository;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.foodcourtservice.infraestructure.out.securitycontext.SecurityContextAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ISecurityContextPort securityContextPort(){
        return new SecurityContextAdapter();
    }
    @Bean
    public IUserFeignClientPort userFeignClientPort(IUserFeignClient userFeignClient, IUserFeignDtoMapper userFeignDtoMapper){
        return new UserFeignClientAdapter(userFeignClient, userFeignDtoMapper);
    }


    @Bean
    public IDishPersistencePort dishPersistencePort(IDishRepository dishRepository, IDishEntityMapper dishEntityMapper){
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(IRestaurantRepository restaurantRepository, IRestaurantEntityMapper restaurantEntityMapper, IRestaurantEmployeeRepository restaurantEmployeeRepository, IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper){
        return new RestaurantMysqlAdapter(restaurantRepository,restaurantEntityMapper, restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
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
    public IDishServicePort dishServicePort(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ISecurityContextPort securityContextPort){
        return new DishUseCase(dishPersistencePort, categoryPersistencePort, restaurantPersistencePort, securityContextPort);
    }

}
