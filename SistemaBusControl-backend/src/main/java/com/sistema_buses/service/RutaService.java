package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaRequest;
import com.sistema_buses.dto.ruta.RutaResponse;

public interface RutaService {
	List<RutaResponse> listar(int pagina);
	List<ParaderoResponse> listarParaderos(Long rutaID);
	RutaResponse encontrarPorID(Long rutaID);
	RutaResponse registrar(RutaRequest request);
	RutaResponse actualizar(Long rutaID, RutaRequest request);
	void eliminarPorID(Long rutaID);
	void agregarParadero(Long rutaID, Long paraderoID);
	void removerParadero(Long rutaID, Long paraderoID);
}
