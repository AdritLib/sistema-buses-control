package com.sistema_buses.dto.incidencia;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaResponse {
    private Long id;
    private Long recorridoID;
    
    private Long usuarioID;
    private String usuarioNombre;
    
    private String descripcion;
    private LocalDateTime fechaHoraSuceso;
    
    private String ruta;
    private String placa;
    private String vehiculo;
    private String estadoRecorrido;
}
