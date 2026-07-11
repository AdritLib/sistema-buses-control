package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoResponse;

public interface VehiculoMantenimientoService {
	List<VehiculoMantenimientoResponse> listar(int pagina);
	VehiculoMantenimientoResponse encontrarPorID(Long vehiculoMantenimientoID);
	VehiculoMantenimientoResponse registrar(VehiculoMantenimientoRequest request);
	VehiculoMantenimientoResponse actualizar(Long vehiculoMantenimientoID, VehiculoMantenimientoRequest request);
	void eliminar(Long vehiculoMantenimientoID);
}
