package com.pragma.foodcourtservice.domain.spi.feignclient;


import com.pragma.foodcourtservice.domain.model.UserModel;

public interface IUserFeignClientPort {

    UserModel findById(Long ownerId);
}
