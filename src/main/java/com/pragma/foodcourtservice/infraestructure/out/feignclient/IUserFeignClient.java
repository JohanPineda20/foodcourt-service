package com.pragma.foodcourtservice.infraestructure.out.feignclient;

import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.UserFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "localhost:8080/user")
public interface IUserFeignClient {

    @GetMapping("/{id}")
    UserFeignDto findById(@PathVariable("id") Long id);

}
