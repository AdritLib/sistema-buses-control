package com.sistema_buses.dto.paradero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.LlegadaEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParaderoConductorResponse {
    private Long rutaParaderoID;
    private Long paraderoID;
    private int orden;
    private String nombre;
    private String direccion;
    private String referencia;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private GenericoEstado estado;
    private LlegadaEstado estadoLlegada;
    private LocalDateTime fechaHoraLlegada;
}
