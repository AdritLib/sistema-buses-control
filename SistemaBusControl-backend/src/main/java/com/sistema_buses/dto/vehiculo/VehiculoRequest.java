package com.sistema_buses.dto.vehiculo;

import com.sistema_buses.enums.VehiculoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoRequest {
	private String placa, marca, modelo;
	private int year, numAsientos;
	private VehiculoEstado estado;
}
