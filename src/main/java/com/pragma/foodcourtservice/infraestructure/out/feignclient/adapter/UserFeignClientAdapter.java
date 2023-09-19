package com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter;

import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.domain.spi.IUserFeignClientPort;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.IUserFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper.IUserFeignDtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFeignClientAdapter implements IUserFeignClientPort {

    private final IUserFeignClient userFeignClient;
    private final IUserFeignDtoMapper userFeignDtoMapper;

    @Override
    public UserModel findById(Long ownerId) {
        return userFeignDtoMapper.mapToUserModel(userFeignClient.findById(ownerId));
    }
}
