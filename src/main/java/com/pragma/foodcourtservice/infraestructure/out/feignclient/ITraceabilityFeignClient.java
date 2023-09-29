package com.pragma.foodcourtservice.infraestructure.out.feignclient;

import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.TrackingFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "traceability-service", url = "localhost:8083/tracking")
public interface ITraceabilityFeignClient {

    @PostMapping
    void trackingOrder(@RequestBody TrackingFeignDto trackingFeignDto);

    @GetMapping
    List<TrackingFeignDto> getHistoryOrder(@RequestParam("orderId") Long orderId);
}
