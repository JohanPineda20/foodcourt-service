package com.pragma.foodcourtservice.domain.spi.feignclient;

public interface IMessengerFeignClientPort {
    void sendMessage(String message, String cellphone);
}
