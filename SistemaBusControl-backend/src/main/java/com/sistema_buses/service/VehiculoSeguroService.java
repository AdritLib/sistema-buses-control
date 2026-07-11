package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.vehiculo.VehiculoSeguroRequest;
import com.sistema_buses.dto.vehiculo.VehiculoSeguroResponse;

public interface VehiculoSeguroService {
	List<VehiculoSeguroResponse> listar(int pagina);
	VehiculoSeguroResponse encontrarPorID(Long vehiculoSeguroID);
	VehiculoSeguroResponse registrar(VehiculoSeguroRequest request);
	VehiculoSeguroResponse actualizar(Long vehiculoSeguroID, VehiculoSeguroRequest request);
}
