package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.EstacionRequest;
import com.sistema_buses.dto.EstacionResponse;

public interface EstacionService {
	List<EstacionResponse> listar(int pagina, int size);
	EstacionResponse encontrarPorID(Long estacionID);
	EstacionResponse registrar(EstacionRequest request);
	EstacionResponse actualizar(Long estacionID, EstacionRequest request);
}
