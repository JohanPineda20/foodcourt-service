package com.pragma.foodcourtservice.infraestructure.out.securitycontext;

import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.domain.spi.securitycontext.ISecurityContextPort;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextAdapter implements ISecurityContextPort {
    @Override
    public Long getIdFromSecurityContext() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userModel.getId();
    }

    @Override
    public String getEmailFromSecurityContext() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userModel.getEmail();
    }
}
