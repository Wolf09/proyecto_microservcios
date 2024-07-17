package com.example.clientepersonaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClientePersonaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientePersonaServiceApplication.class, args);
	}

}
