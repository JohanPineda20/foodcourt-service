package com.pragma.foodcourtservice.infraestructure.out.feignclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserFeignDto {
    private Long id;
    private String name;
    private String lastname;
    private String documentNumber;
    private String cellphone;
    private String email;
    private String role;
}
