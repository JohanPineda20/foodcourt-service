package com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper;

import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.UserFeignDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserFeignDtoMapper {

    UserModel mapToUserModel(UserFeignDto userFeignDto);

}
