package com.pragma.foodcourtservice.infraestructure.out.feignclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignClientRequestInterceptor implements RequestInterceptor {

    private final HttpServletRequest httpServletRequest;
    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", httpServletRequest.getHeader("Authorization"));
    }
}
