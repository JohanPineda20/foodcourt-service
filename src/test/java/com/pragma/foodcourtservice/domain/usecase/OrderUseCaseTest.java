package com.pragma.foodcourtservice.domain.usecase;

import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import com.pragma.foodcourtservice.domain.model.*;
import com.pragma.foodcourtservice.domain.spi.feignclient.IMessengerFeignClientPort;
import com.pragma.foodcourtservice.domain.spi.persistence.IDishPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.foodcourtservice.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.foodcourtservice.domain.spi.securitycontext.ISecurityContextPort;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    @Mock
    IMessengerFeignClientPort messengerFeignClientPort;

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

    @Test
    void getAllOrdersByRestaurantAndStatus(){
        Integer page = 1;
        Integer size = 10;
        String status = "PENDING";
        Long employeeId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(employeeId);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId)).thenReturn(restaurantEmployeeModel);
        List<OrderModel> orderModelList = List.of(new OrderModel(1L, null, null, StatusEnumModel.PENDING, null, null, null));
        when(orderPersistencePort.getAllOrdersByRestaurant(page, size, restaurantEmployeeModel.getRestaurant().getId())).thenReturn(orderModelList);
        when(orderPersistencePort.getAllDishesByOrderId(1L)).thenReturn(List.of(new OrderDishModel()));
        when(orderPersistencePort.getAllOrdersByRestaurantAndStatus(page, size, restaurantEmployeeModel.getRestaurant().getId(), StatusEnumModel.PENDING)).thenReturn(orderModelList);

        List<OrderModel> orderModelList1 = orderUseCase.getAllOrdersByRestaurantAndStatus(page, size, status);

        //La lista orderModelList1 porque la order que tiene es PENDING
        assertEquals(orderModelList, orderModelList1);
    }

    @Test
    void getAllOrdersByRestaurantAndStatusNotMatches(){
        Integer page = 1;
        Integer size = 10;
        String status = "PENDING";
        Long employeeId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(employeeId);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId)).thenReturn(restaurantEmployeeModel);
        List<OrderModel> orderModelList = List.of(new OrderModel(1L, null, null, StatusEnumModel.CANCELLED, null, null, null));
        when(orderPersistencePort.getAllOrdersByRestaurant(page, size, restaurantEmployeeModel.getRestaurant().getId())).thenReturn(orderModelList);
        when(orderPersistencePort.getAllDishesByOrderId(1L)).thenReturn(List.of(new OrderDishModel()));
        when(orderPersistencePort.getAllOrdersByRestaurantAndStatus(page, size, restaurantEmployeeModel.getRestaurant().getId(), StatusEnumModel.PENDING)).thenReturn(new ArrayList<>());

        assertThrows(DataNotFoundException.class, () -> orderUseCase.getAllOrdersByRestaurantAndStatus(page, size, status));
    }

    @Test
    void getAllOrdersByRestaurantAndStatusIsWrong(){
        Integer page = 1;
        Integer size = 10;
        String status = "PENDIN";
        Long employeeId = 1L;
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(employeeId);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(employeeId)).thenReturn(restaurantEmployeeModel);
        List<OrderModel> orderModelList = List.of(new OrderModel(1L, null, null, StatusEnumModel.CANCELLED, null, null, null));
        when(orderPersistencePort.getAllOrdersByRestaurant(page, size, restaurantEmployeeModel.getRestaurant().getId())).thenReturn(orderModelList);
        when(orderPersistencePort.getAllDishesByOrderId(1L)).thenReturn(List.of(new OrderDishModel()));

        assertThrows(DomainException.class, () -> orderUseCase.getAllOrdersByRestaurantAndStatus(page, size, status));
    }
    @Test
    void takeOrder(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.PENDING);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        orderUseCase.takeOrder(orderId);

        verify(orderPersistencePort, times(1)).save(orderModel);
        assertEquals(restaurantEmployeeModel, orderModel.getRestaurantEmployee());
        assertEquals(StatusEnumModel.IN_PREPARATION, orderModel.getStatus());
    }
    @Test
    void takeOrderNotFound() {
        Long orderId = 1L;
        when(orderPersistencePort.findById(orderId)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> orderUseCase.takeOrder(orderId));

        verify(orderPersistencePort, never()).save(any());
    }
    @Test
    void takeOrderEmployeeNotWorkInRestaurant(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.PENDING);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(5L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        assertThrows(DomainException.class, () -> orderUseCase.takeOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertNull(orderModel.getRestaurantEmployee());
        assertEquals(StatusEnumModel.PENDING, orderModel.getStatus());
    }
    @Test
    void takeOrderStatusIsNotPending(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.READY);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        assertThrows(DomainException.class, () -> orderUseCase.takeOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertNull(orderModel.getRestaurantEmployee());
        assertEquals(StatusEnumModel.READY, orderModel.getStatus());
    }

    @Test
    void readyOrder(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setRestaurantEmployee(new RestaurantEmployeeModel(1L, null, null));
        orderModel.setCustomerId(5L);
        orderModel.setCreatedAt(LocalDateTime.of(2023,10,16,7,8));
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setId(1L);
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        orderUseCase.readyOrder(orderId);

        verify(orderPersistencePort, times(1)).save(orderModel);
        assertEquals(StatusEnumModel.READY, orderModel.getStatus());
    }

    @Test
    void readyOrderWasTakenByAnotherEmployee(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setRestaurantEmployee(new RestaurantEmployeeModel(1L, null, null));
        orderModel.setCustomerId(5L);
        orderModel.setCreatedAt(LocalDateTime.of(2023,10,16,7,8));
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setId(9L);
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        assertThrows(DomainException.class, () -> orderUseCase.readyOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertEquals(StatusEnumModel.IN_PREPARATION, orderModel.getStatus());
    }

    @Test
    void readyOrderStatusIsNotInPreparation(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.CANCELLED);
        orderModel.setRestaurantEmployee(new RestaurantEmployeeModel(1L, null, null));
        orderModel.setCustomerId(5L);
        orderModel.setCreatedAt(LocalDateTime.of(2023,10,16,7,8));
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setId(1L);
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        assertThrows(DomainException.class, () -> orderUseCase.readyOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertEquals(StatusEnumModel.CANCELLED, orderModel.getStatus());
    }

    @Test
    void readyOrderFeignException(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setRestaurantEmployee(new RestaurantEmployeeModel(1L, null, null));
        orderModel.setCustomerId(5L);
        orderModel.setCreatedAt(LocalDateTime.of(2023,10,16,7,8));
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setId(1L);
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);
        doThrow(FeignException.class).when(messengerFeignClientPort).sendMessage(anyString(),anyString());

        assertThrows(DomainException.class,() -> orderUseCase.readyOrder(orderId));

        verify(orderPersistencePort, times(1)).save(orderModel);
        assertEquals(StatusEnumModel.READY, orderModel.getStatus());
    }
    @Test
    void deliverOrder(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.READY);
        orderModel.setRestaurantEmployee(new RestaurantEmployeeModel(1L, null, null));
        orderModel.setCustomerId(5L);
        orderModel.setCreatedAt(LocalDateTime.of(2023,10,16,7,8));
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setId(1L);
        restaurantEmployeeModel.setRestaurant(new RestaurantModel(1L, null, null, null, null, null, null));
        when(restaurantPersistencePort.findRestaurantEmployeeByEmployeeId(1L)).thenReturn(restaurantEmployeeModel);

        orderUseCase.deliverOrder(orderId, "juank1516102023");

        verify(orderPersistencePort, times(1)).save(orderModel);
        assertEquals(StatusEnumModel.DELIVERED, orderModel.getStatus());
    }
    @Test
    void cancelOrder(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.PENDING);
        orderModel.setCustomerId(1L);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);

        orderUseCase.cancelOrder(orderId);

        verify(orderPersistencePort, times(1)).save(orderModel);
        assertEquals(StatusEnumModel.CANCELLED, orderModel.getStatus());
    }

    @Test
    void cancelOrderCustomerIsNotTheSame(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.PENDING);
        orderModel.setCustomerId(1L);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(5L);

        assertThrows(DomainException.class, () -> orderUseCase.cancelOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertEquals(StatusEnumModel.PENDING, orderModel.getStatus());
    }
    @Test
    void cancelOrderStatusIsNotPending(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setCustomerId(1L);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);

        assertThrows(DomainException.class, () -> orderUseCase.cancelOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertEquals(StatusEnumModel.IN_PREPARATION, orderModel.getStatus());
    }

    @Test
    void cancelOrderStatusIsNotPendingFeignException(){
        Long orderId = 1L;
        OrderModel orderModel = createExampleOrderModel();
        orderModel.setStatus(StatusEnumModel.IN_PREPARATION);
        orderModel.setCustomerId(1L);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(securityContextPort.getIdFromSecurityContext()).thenReturn(1L);
        doThrow(FeignException.class).when(messengerFeignClientPort).sendMessage(anyString(), anyString());

        assertThrows(DomainException.class, () -> orderUseCase.cancelOrder(orderId));

        verify(orderPersistencePort, never()).save(orderModel);
        assertEquals(StatusEnumModel.IN_PREPARATION, orderModel.getStatus());
    }

    private OrderModel createExampleOrderModel(){
        OrderModel orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setRestaurant(new RestaurantModel(1L, "juank", null, null, null, null, null));
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