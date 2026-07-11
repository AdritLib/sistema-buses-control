package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistema_buses.dto.RegistroResponse;
import com.sistema_buses.repository.RegistroRepository;
import com.sistema_buses.service.RegistroService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroServiceImpl implements RegistroService {
	private final RegistroRepository registroRepository;
	
	@Override
	public List<RegistroResponse> listar(int pagina, int tamanio) {
		return registroRepository.listar(PageRequest.of(pagina, tamanio, Sort.by("fecha").descending())).toList();
	}
}
