package com.sistema_buses.dto.paradero;

import java.time.LocalDateTime;

import com.sistema_buses.enums.LlegadaEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParaderoRecorridoConductorResponse {
    private Long idRutaParadero;
    private int orden;
    private String nombre;
    private String direccion;
    private String referencia;
    private LlegadaEstado estadoLlegada;
    private LocalDateTime horaLlegadaReal;
    private String observaciones;
}
