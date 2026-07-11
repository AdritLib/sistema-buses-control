package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaParaderoRequest;
import com.sistema_buses.dto.ruta.RutaParaderoResponse;

public interface RutaParaderoService {
	List<RutaParaderoResponse> listar(int pagina);
	RutaParaderoResponse encontrarPorID(Long estacionID);
	RutaParaderoResponse registrar(RutaParaderoRequest request);
	RutaParaderoResponse actualizar(Long estacionID, RutaParaderoRequest request);
	List<ParaderoResponse> listarPorRuta(Long idRuta);
}
