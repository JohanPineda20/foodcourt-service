package com.pragma.foodcourtservice.infraestructure.out.feignclient.adapter;

import com.pragma.foodcourtservice.domain.spi.feignclient.IMessengerFeignClientPort;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.IMessengerFeignClient;
import com.pragma.foodcourtservice.infraestructure.out.feignclient.dto.MessageFeignDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessengerFeignClientAdapter implements IMessengerFeignClientPort {

    private final IMessengerFeignClient messengerFeignClient;

    @Override
    public void sendMessage(String message, String cellphone) {
        MessageFeignDto messageFeignDto = new MessageFeignDto(message, cellphone);
        messengerFeignClient.sendMessage(messageFeignDto);
    }
}
