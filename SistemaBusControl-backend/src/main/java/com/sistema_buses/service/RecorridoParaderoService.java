package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.recorrido.RecorridoParaderoRequest;
import com.sistema_buses.dto.recorrido.RecorridoParaderoResponse;

public interface RecorridoParaderoService {
	List<RecorridoParaderoResponse> listar(int pagina);
	RecorridoParaderoResponse encontrarPorID(Long recorridoParaderoID);
	RecorridoParaderoResponse registrar(RecorridoParaderoRequest request);
	RecorridoParaderoResponse actualizar(Long recorridoParaderoID, RecorridoParaderoRequest request);
}
