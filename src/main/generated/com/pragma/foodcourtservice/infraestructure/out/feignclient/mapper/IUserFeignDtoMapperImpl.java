package com.pragma.foodcourtservice.infraestructure.out.feignclient.mapper;

import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.UserFeignDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-02T10:40:20-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class IUserFeignDtoMapperImpl implements IUserFeignDtoMapper {

    @Override
    public UserModel mapToUserModel(UserFeignDto userFeignDto) {
        if ( userFeignDto == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        userModel.setId( userFeignDto.getId() );
        userModel.setName( userFeignDto.getName() );
        userModel.setLastname( userFeignDto.getLastname() );
        userModel.setDocumentNumber( userFeignDto.getDocumentNumber() );
        userModel.setCellphone( userFeignDto.getCellphone() );
        userModel.setEmail( userFeignDto.getEmail() );
        userModel.setRole( userFeignDto.getRole() );

        return userModel;
    }
}
