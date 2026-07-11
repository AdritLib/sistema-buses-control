package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.paradero.ParaderoRequest;
import com.sistema_buses.dto.paradero.ParaderoResponse;

public interface ParaderoService {
	List<ParaderoResponse> listar(int pagina, int size);
	ParaderoResponse encontrarPorID(Long paraderoID);
	ParaderoResponse registrar(ParaderoRequest request);
	ParaderoResponse actualizar(Long paraderoID, ParaderoRequest request);
	void eliminarPorID(Long paraderoID);
}
