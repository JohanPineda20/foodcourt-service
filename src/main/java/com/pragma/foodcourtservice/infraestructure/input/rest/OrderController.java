package com.pragma.foodcourtservice.infraestructure.input.rest;

import com.pragma.foodcourtservice.application.dto.request.OrderRequest;
import com.pragma.foodcourtservice.application.dto.response.DishResponse;
import com.pragma.foodcourtservice.application.dto.response.OrderResponse;
import com.pragma.foodcourtservice.application.dto.response.TrackingResponse;
import com.pragma.foodcourtservice.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Controller")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Restaurant or Dish not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Conflict: Customer cannot create a new order because has an order in progress, Dish does not belong to the restaurant, Dish is not available", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @SecurityRequirement(name = "jwt")
    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Void> save(@Valid @RequestBody OrderRequest orderRequest){
        orderHandler.save(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "Get a list of orders by employee's restaurant and filter by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders by employee's restaurant and filter by statusy", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Employee not found, There are no orders in the restaurant, There are no orders in the restaurant with that status", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Status does not exist or is wrong!!!", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @SecurityRequirement(name = "jwt")
    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<OrderResponse>> getAllOrdersByRestaurantAndStatus(@RequestParam(defaultValue = "0") Integer page,
                                                                                @RequestParam(defaultValue = "10") Integer size,
                                                                                @RequestParam(required = false) String status){
        return ResponseEntity.ok(orderHandler.getAllOrdersByRestaurantAndStatus(page, size, status));
    }

    @Operation(summary = "Employee can take a pending order and change its status to in_preparation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order taken by a restaurant employee", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Order not found, Employee not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Employee does not work in the restaurant, Order cannot be taken", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @SecurityRequirement(name = "jwt")
    @PatchMapping("/{id}/take")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Void> takeOrder(@PathVariable Long id){
        orderHandler.takeOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Employee can mark a in_preparation order as ready and send sms to customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order marked as ready and sms sent to customer", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Order not found, Employee not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Employee does not work in the restaurant, Order cannot be ready, Employee cannot mark the order as ready because the order was taken by another employee, FeignException", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @SecurityRequirement(name = "jwt")
    @PatchMapping("/{id}/ready")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Void> readyOrder(@PathVariable Long id){
        orderHandler.readyOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Employee can deliver a ready order to customer when security pin is right")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order delivered", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Order not found, Employee not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Employee does not work in the restaurant, Order cannot be delivered, Employee cannot deliver the order because the order was taken by another employee, Order cannot be delivered because the security pin is wrong", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @SecurityRequirement(name = "jwt")
    @PatchMapping("/{id}/deliver")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<Void> deliverOrder(@PathVariable Long id, @RequestParam String pin){
        orderHandler.deliverOrder(id, pin);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Customer can cancel the orden if its status is pending")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order canceled", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "The customer must be the same customer who placed the order, Order cannot be canceled", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @SecurityRequirement(name = "jwt")
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id){
        orderHandler.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get history of order status changes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "History checked", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TrackingResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request: wrong input data", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "The customer must be the same customer who placed the order, FeignException", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @SecurityRequirement(name = "jwt")
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<TrackingResponse>> getHistoryOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderHandler.getHistoryOrder(id));
    }

}
