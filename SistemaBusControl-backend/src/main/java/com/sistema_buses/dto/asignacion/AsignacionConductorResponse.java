package com.sistema_buses.dto.asignacion;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sistema_buses.dto.ruta.RutaConductorResponse;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.enums.GenericoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionConductorResponse {
    private Long id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private GenericoEstado estado;
    private VehiculoResponse vehiculo;
    private RutaConductorResponse ruta;
}
