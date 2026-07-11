package com.sistema_buses.service;

import java.util.List;

import com.sistema_buses.dto.asignacion.AsignacionConductorResponse;
import com.sistema_buses.dto.recorrido.RecorridoConductorResponse;
import com.sistema_buses.dto.ruta.RutaConductorResponse;

public interface ConductorService {
    AsignacionConductorResponse obtenerAsignacionDelDia(Long usuarioID);
    RutaConductorResponse obtenerRutaDelDia(Long usuarioID);
    List<RecorridoConductorResponse> obtenerHistorial(Long usuarioID, int pagina, int size);
}
