package com.pragma.foodcourtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FoodcourtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodcourtServiceApplication.class, args);
	}

}
