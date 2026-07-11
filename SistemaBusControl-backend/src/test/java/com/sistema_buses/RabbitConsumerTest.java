package com.sistema_buses;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.model.Registro;
import com.sistema_buses.repository.RegistroRepository;
import com.sistema_buses.service.rabbitmq.RabbitConsumer;

@ExtendWith(MockitoExtension.class)
public class RabbitConsumerTest {
	@Mock
	private RegistroRepository registroRepository;
	
	@InjectMocks
	private RabbitConsumer consumer;
	
	@Test
	void deberiaGuardarRegistroEnBaseDeDatosAlConsumir() {
		Registro dato = new Registro(1L, RegistroAccion.INSERTAR, 1L, "prueba de registro", "Test", LocalDateTime.now());
		when(registroRepository.save(any())).thenReturn(dato);
		
		consumer.recibir(dato);
		verify(registroRepository, times(1)).save(dato);
	}
}
