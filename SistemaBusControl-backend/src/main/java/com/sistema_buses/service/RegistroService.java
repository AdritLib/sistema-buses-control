package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.RegistroResponse;

public interface RegistroService {
	List<RegistroResponse> listar(int pagina, int tamanio);
}
