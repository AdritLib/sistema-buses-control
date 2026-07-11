package com.sistema_buses.dto.paradero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaderoConductorResponse {
    private Long rutaParaderoID;
    private Long idRutaParadero;
    private Long paraderoID;
    private int orden;
    private String nombre;
    private String direccion;
    private String referencia;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String estado;
    private String estadoLlegada;
    private LocalDateTime fechaHoraLlegada;
    private LocalDateTime horaLlegadaReal;
    private String observaciones;

    public Long getRutaParaderoID() {
        return rutaParaderoID != null ? rutaParaderoID : idRutaParadero;
    }

    public Long getIdRutaParadero() {
        return idRutaParadero != null ? idRutaParadero : rutaParaderoID;
    }

    public LocalDateTime getFechaHoraLlegada() {
        return fechaHoraLlegada != null ? fechaHoraLlegada : horaLlegadaReal;
    }

    public LocalDateTime getHoraLlegadaReal() {
        return horaLlegadaReal != null ? horaLlegadaReal : fechaHoraLlegada;
    }
}
