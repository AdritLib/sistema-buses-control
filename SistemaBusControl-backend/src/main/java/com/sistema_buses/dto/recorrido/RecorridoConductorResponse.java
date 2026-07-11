package com.sistema_buses.dto.recorrido;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sistema_buses.dto.ruta.RutaConductorResponse;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.enums.RecorridoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecorridoConductorResponse {
    private Long id;
    private Long asignacionID;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private RecorridoEstado estado;
    private VehiculoResponse vehiculo;
    private RutaConductorResponse ruta;
}
