package com.sistema_buses.service;

import java.util.List;
import java.util.Optional;

import com.sistema_buses.dto.paradero.ParaderoConductorResponse;
import com.sistema_buses.dto.paradero.ParaderoRecorridoConductorResponse;
import com.sistema_buses.dto.recorrido.MarcarLlegadaRequest;
import com.sistema_buses.dto.recorrido.RecorridoConductorResponse;
import com.sistema_buses.dto.recorrido.RecorridoRequest;
import com.sistema_buses.dto.recorrido.RecorridoResponse;

public interface RecorridoService {
	List<RecorridoResponse> listar(int pagina);
	RecorridoResponse encontrarPorID(Long recorridoID);
	RecorridoResponse registrar(RecorridoRequest request);
	RecorridoResponse actualizar(Long recorridoID, RecorridoRequest request);
	

	RecorridoConductorResponse iniciarParaConductor(Long asignacionID);
	RecorridoConductorResponse obtenerDetalleParaConductor(Long recorridoID);
	Optional<RecorridoConductorResponse> obtenerActivoParaConductor(Long usuarioID);
	Optional<RecorridoConductorResponse> obtenerUltimoParaAsignacion(Long asignacionID);
	List<ParaderoConductorResponse> listarParaderosDelRecorrido(Long recorridoID);
	List<ParaderoRecorridoConductorResponse> listarDetalleParaderosDelRecorrido(Long recorridoID);
	ParaderoConductorResponse marcarLlegada(Long recorridoID, Long rutaParaderoID, MarcarLlegadaRequest request);
	ParaderoConductorResponse marcarLlegadaParaConductor(Long rutaParaderoID, MarcarLlegadaRequest request);
	RecorridoConductorResponse finalizarParaConductor(Long recorridoID);
	RecorridoConductorResponse finalizarActivoParaConductor();
}
