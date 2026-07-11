package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.incidencia.IncidenciaConductorRequest;
import com.sistema_buses.dto.incidencia.IncidenciaRequest;
import com.sistema_buses.dto.incidencia.IncidenciaResponse;

public interface IncidenciaService {
	List<IncidenciaResponse> listar(int pagina);
	IncidenciaResponse encontrarPorID(Long incidencia);
	IncidenciaResponse registrar(IncidenciaRequest request);
	IncidenciaResponse registrarComoConductor(IncidenciaConductorRequest request);
	
	IncidenciaResponse actualizar(Long incidencia, IncidenciaRequest request);
	void eliminar(Long id);
	List<IncidenciaResponse> listarParaConductor(Long usuarioID, int pagina, int size);
}
