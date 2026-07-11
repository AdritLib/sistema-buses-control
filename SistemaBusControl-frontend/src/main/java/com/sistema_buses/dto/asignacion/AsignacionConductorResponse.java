package com.sistema_buses.dto.asignacion;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sistema_buses.dto.ruta.RutaConductorResponse;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionConductorResponse {
    private Long id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
    private VehiculoResponse vehiculo;
    private RutaConductorResponse ruta;
}
