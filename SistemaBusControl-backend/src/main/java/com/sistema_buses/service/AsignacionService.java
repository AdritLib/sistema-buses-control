package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.asignacion.AsignacionRequest;
import com.sistema_buses.dto.asignacion.AsignacionResponse;

public interface AsignacionService {
	List<AsignacionResponse> listar(int pagina);
	AsignacionResponse obtenerPorID(Long asignacionID);
	AsignacionResponse registrar(AsignacionRequest request);
	AsignacionResponse actualizar(Long asignacionID, AsignacionRequest request);
	void asignarConductor(Long asignacionID, Long usuarioID);
	void asignarRuta(Long asignacionID, Long rutaID);
	void eliminarPorID(Long id);
}
