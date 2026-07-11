package com.sistema_buses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SistemaBusesFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaBusesFrontendApplication.class, args);
	}

}
