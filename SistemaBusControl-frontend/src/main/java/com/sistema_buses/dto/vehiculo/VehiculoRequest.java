package com.sistema_buses.dto.vehiculo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoRequest {
    private String placa;
    private String marca;
    private String modelo;
    private Integer year;
    private Integer numAsientos;
    private String estado;
}