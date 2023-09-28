package com.pragma.foodcourtservice.infraestructure.configuration;

import com.pragma.foodcourtservice.domain.api.ICategoryServicePort;
import com.pragma.foodcourtservice.domain.api.IDishServicePort;
import com.pragma.foodcourtservice.domain.api.IOrderServicePort;
import com.pragma.foodcourtservice.domain.api.IRestaurantServicePort;
import com.pragma.foodcourtservice.domain.spi.feignclient.IMessengerFeignClientPort;
import com.pragma.foodcourtservice.domain.spi.feignclient.IUserFeignClientPort;
import com.pragma.foodcourtservice.domain.spi.persistence.ICategoryPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.securitycontext.ISecurityContextPort;
import com.pragma.foodcourtservice.domain.usecase.CategoryUseCase;
import com.pragma.foodcourtservice.domain.usecase.DishUseCase;
import com.pragma.foodcourtservice.domain.usecase.OrderUseCase;
import com.pragma.foodcourtservice.domain.usecase.RestaurantUseCase;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.IMessengerFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.IUserFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter.MessengerFeignClientAdapter;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter.UserFeignClientAdapter;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper.IUserFeignDtoMapper;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.CategoryMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.DishMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.OrderMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.adapter.RestaurantMysqlAdapter;
import com.pragma.foodcourtservice.infraestructure.out.jpa.mapper.*;
import com.pragma.foodcourtservice.infraestructure.out.jpa.repository.*;
import com.pragma.foodcourtservice.infraestructure.out.securitycontext.SecurityContextAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUserFeignClientPort userFeignClientPort(IUserFeignClient userFeignClient, IUserFeignDtoMapper userFeignDtoMapper){
        return new UserFeignClientAdapter(userFeignClient, userFeignDtoMapper);
    }
    @Bean
    public ISecurityContextPort securityContextPort(){
        return new SecurityContextAdapter();
    }

    @Bean
    public IMessengerFeignClientPort messengerFeignClientPort(IMessengerFeignClient messengerFeignClient){
        return new MessengerFeignClientAdapter(messengerFeignClient);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper){
        return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(IRestaurantRepository restaurantRepository, IRestaurantEntityMapper restaurantEntityMapper, IRestaurantEmployeeRepository restaurantEmployeeRepository, IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper){
        return new RestaurantMysqlAdapter(restaurantRepository,restaurantEntityMapper, restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }
    @Bean
    public IDishPersistencePort dishPersistencePort(IDishRepository dishRepository, IDishEntityMapper dishEntityMapper){
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IOrderPersistencePort orderPersistencePort(IOrderRepository orderRepository, IOrderEntityMapper orderEntityMapper, IOrderDishRepository orderDishRepository, IOrderDishEntityMapper orderDishEntityMapper){
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper, orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(ICategoryPersistencePort categoryPersistencePort){
        return new CategoryUseCase(categoryPersistencePort);
    }
    @Bean
    public IRestaurantServicePort restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClientPort){
        return new RestaurantUseCase(restaurantPersistencePort, userFeignClientPort);
    }
    @Bean
    public IDishServicePort dishServicePort(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ISecurityContextPort securityContextPort){
        return new DishUseCase(dishPersistencePort, categoryPersistencePort, restaurantPersistencePort, securityContextPort);
    }
    @Bean
    public IOrderServicePort orderServicePort(IOrderPersistencePort orderPersistencePort, ISecurityContextPort securityContextPort, IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IMessengerFeignClientPort messengerFeignClientPort){
        return new OrderUseCase(orderPersistencePort, securityContextPort, restaurantPersistencePort, dishPersistencePort, messengerFeignClientPort);
    }
}
