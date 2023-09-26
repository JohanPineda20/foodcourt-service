package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.*;
import com.pragma.foodcourtservice.domain.spi.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IOrderPersistencePort;
import com.pragma.foodcourtservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.ISecurityContextPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {
    @InjectMocks
    OrderUseCase orderUseCase;
    @Mock
    IOrderPersistencePort orderPersistencePort;
    @Mock
    ISecurityContextPort securityContextPort;
    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    IDishPersistencePort dishPersistencePort;

    @Test
    void save() {
    OrderModel orderModel = createExampleOrderModel();
    Long customerId = 1L;
    when(securityContextPort.getIdFromSecurityContext()).thenReturn(customerId);
    when(orderPersistencePort.findByCustomerId(customerId)).thenReturn(List.of(new OrderModel(null, null, null, StatusEnumModel.CANCELLED, null, null, null)));
    when(restaurantPersistencePort.findById(orderModel.getRestaurant().getId())).thenReturn(new RestaurantModel(1L, null, null, null, null, null, null));
    when(dishPersistencePort.findById(1L)).thenReturn(new DishModel(null,null,null,null,null, true, new RestaurantModel(1L, null, null, null, null, null, null), null));

    orderUseCase.save(orderModel);

    verify(orderPersistencePort, times(1)).save(orderModel);
    verify(orderPersistencePort, times(1)).saveOrderDish(any());
    assertEquals(StatusEnumModel.PENDING, orderModel.getStatus());
    assertEquals(customerId, orderModel.getCustomerId());
    }

    @Test
    void saveCustomerCannotCreateANewOrder() {
        OrderModel orderModel = createExampleOrderModel();
        Long customerId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(customerId);
        when(orderPersistencePort.findByCustomerId(customerId)).thenReturn(List.of(new OrderModel(null, null, null, StatusEnumModel.PENDING, null, null, null)));

        assertThrows(DomainException.class, () -> orderUseCase.save(orderModel));

        verify(orderPersistencePort, never()).save(orderModel);
        verify(orderPersistencePort, never()).saveOrderDish(any());
        assertNotEquals(StatusEnumModel.PENDING, orderModel.getStatus());
        assertNotEquals(customerId, orderModel.getCustomerId());
    }

    @Test
    void saveRestaurantNotFound() {
        OrderModel orderModel = createExampleOrderModel();
        Long customerId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(customerId);
        when(orderPersistencePort.findByCustomerId(customerId)).thenReturn(List.of(new OrderModel(null, null, null, StatusEnumModel.CANCELLED, null, null, null)));
        when(restaurantPersistencePort.findById(orderModel.getRestaurant().getId())).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> orderUseCase.save(orderModel));

        verify(orderPersistencePort, never()).save(orderModel);
        verify(orderPersistencePort, never()).saveOrderDish(any());
        assertNotEquals(StatusEnumModel.PENDING, orderModel.getStatus());
        assertNotEquals(customerId, orderModel.getCustomerId());
    }

    @Test
    void saveDishNotFound() {
        OrderModel orderModel = createExampleOrderModel();
        Long customerId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(customerId);
        when(orderPersistencePort.findByCustomerId(customerId)).thenReturn(List.of(new OrderModel(null, null, null, StatusEnumModel.CANCELLED, null, null, null)));
        when(restaurantPersistencePort.findById(orderModel.getRestaurant().getId())).thenReturn(new RestaurantModel(1L, null, null, null, null, null, null));
        when(dishPersistencePort.findById(1L)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> orderUseCase.save(orderModel));

        verify(orderPersistencePort, times(1)).save(orderModel);
        verify(orderPersistencePort, never()).saveOrderDish(any());
        assertEquals(StatusEnumModel.PENDING, orderModel.getStatus());
        assertEquals(customerId, orderModel.getCustomerId());
    }

    @Test
    void saveDishDoesNotBelongToRestaurant() {
        OrderModel orderModel = createExampleOrderModel();
        Long customerId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(customerId);
        when(orderPersistencePort.findByCustomerId(customerId)).thenReturn(List.of(new OrderModel(null, null, null, StatusEnumModel.CANCELLED, null, null, null)));
        when(restaurantPersistencePort.findById(orderModel.getRestaurant().getId())).thenReturn(new RestaurantModel(1L, null, null, null, null, null, null));
        when(dishPersistencePort.findById(1L)).thenReturn(new DishModel(null,null,null,null,null, true, new RestaurantModel(7L, null, null, null, null, null, null), null));

        assertThrows(DomainException.class, () -> orderUseCase.save(orderModel));

        verify(orderPersistencePort, times(1)).save(orderModel);
        verify(orderPersistencePort, never()).saveOrderDish(any());
        assertEquals(StatusEnumModel.PENDING, orderModel.getStatus());
        assertEquals(customerId, orderModel.getCustomerId());
    }

    @Test
    void saveDishIsNotActive() {
        OrderModel orderModel = createExampleOrderModel();
        Long customerId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(customerId);
        when(orderPersistencePort.findByCustomerId(customerId)).thenReturn(List.of(new OrderModel(null, null, null, StatusEnumModel.CANCELLED, null, null, null)));
        when(restaurantPersistencePort.findById(orderModel.getRestaurant().getId())).thenReturn(new RestaurantModel(1L, null, null, null, null, null, null));
        when(dishPersistencePort.findById(1L)).thenReturn(new DishModel(null,null,null,null,null, false, new RestaurantModel(1L, null, null, null, null, null, null), null));

        assertThrows(DomainException.class, () -> orderUseCase.save(orderModel));

        verify(orderPersistencePort, times(1)).save(orderModel);
        verify(orderPersistencePort, never()).saveOrderDish(any());
        assertEquals(StatusEnumModel.PENDING, orderModel.getStatus());
        assertEquals(customerId, orderModel.getCustomerId());
    }
        private OrderModel createExampleOrderModel(){
        OrderModel orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        orderModel.setDishes(createExampleDishes());
        return orderModel;
    }

    private List<OrderDishModel> createExampleDishes(){
        OrderDishModel orderDishModel = new OrderDishModel();
        orderDishModel.setDish(new DishModel(1L,null,null,null,null,null,null,null));
        orderDishModel.setAmount(20L);
        return List.of(orderDishModel);
    }


}