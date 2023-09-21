package com.pragma.foodcourtservice.infraestructure.out.securitycontext;

import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.domain.spi.ISecurityContextPort;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextAdapter implements ISecurityContextPort {
    @Override
    public Long getIdFromSecurityContext() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userModel.getId();
    }
}
