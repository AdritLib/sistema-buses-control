package com.sistema_buses.dto.paradero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaderoResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String referencia;
    private Double latitud;
    private Double longitud;
    private String estado;
}