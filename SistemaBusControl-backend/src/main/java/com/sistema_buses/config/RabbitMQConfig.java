package com.sistema_buses.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	public static final String COLA = "cola.sistema.buses";
	public static final String INTERCAMBIO = "intercambio.sistema.buses";
	
	@Bean
	Queue queue() {
		return new Queue(COLA);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(INTERCAMBIO);
	}
	
	@Bean
	JacksonJsonMessageConverter messageConverter() {
		return new JacksonJsonMessageConverter();
	}
}
