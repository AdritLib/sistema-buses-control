package com.sistema_buses.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sistema_buses.config.RabbitMQConfig;
import com.sistema_buses.model.Registro;
import com.sistema_buses.repository.RegistroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitConsumer {
	private final RegistroRepository registroRepository;
	
	@RabbitListener(queues = RabbitMQConfig.COLA)
	public void recibir(Registro registro) {
		registroRepository.save(registro);
	}
}
