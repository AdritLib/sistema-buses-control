package com.sistema_buses.dto.vehiculo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoMantenimientoResponse {
	private Long id;
	private Long vehiculoID;
	private String vehiculoPlaca;
	private LocalDate fechaInicio, fechaFin;
	private String descripcion;
	private String estado;
}