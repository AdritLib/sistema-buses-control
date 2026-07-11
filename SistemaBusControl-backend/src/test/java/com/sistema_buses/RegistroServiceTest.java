package com.sistema_buses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sistema_buses.dto.RegistroResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.repository.RegistroRepository;
import com.sistema_buses.service.implementado.RegistroServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RegistroServiceTest {
	@Mock
	private RegistroRepository registroRepository;

	@InjectMocks
	private RegistroServiceImpl registroService;
	
	@Test
	void debeRetornarListadoNoVacio() {
		List<RegistroResponse> elementos = List.of(
				new RegistroResponse(1L, RegistroAccion.INSERTAR, "Inserto nuevo usuario", "Usuario", LocalDateTime.now(), 0L, "SISTEMA")
				);
		Page<RegistroResponse> lista = new PageImpl<>(elementos, PageRequest.of(0, 10), 1);
		
		Mockito.when(registroRepository.listar(any(Pageable.class))).thenReturn(lista);
		assertThat(!registroService.listar(0, 10).isEmpty());
	}
}
