package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.vehiculo.VehiculoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;

public interface VehiculoService {
	List<VehiculoResponse> listar(int pagina);
	VehiculoResponse encontrarPorID(Long vehiculoID);
	VehiculoResponse registrar(VehiculoRequest request);
	VehiculoResponse actualizar(Long vehiculoID, VehiculoRequest request);
	void eliminarPorID(Long vehiculoID);
}
