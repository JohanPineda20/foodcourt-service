package com.pragma.foodcourtservice.infraestructure.out.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MessageFeignDto {
    private String message;
    private String cellphone;
}
