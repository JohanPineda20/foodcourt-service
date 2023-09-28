package com.pragma.foodcourtservice.infraestructure.out.feignclient;

import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.MessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messenger-service", url = "localhost:8082/message")
public interface IMessengerFeignClient {
    @PostMapping
    void sendMessage(@RequestBody MessageRequest messageRequest);
}
