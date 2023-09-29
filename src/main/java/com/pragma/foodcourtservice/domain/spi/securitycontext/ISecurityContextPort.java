package com.pragma.foodcourtservice.domain.spi.securitycontext;

public interface ISecurityContextPort {
    Long getIdFromSecurityContext();
    String getEmailFromSecurityContext();
}
