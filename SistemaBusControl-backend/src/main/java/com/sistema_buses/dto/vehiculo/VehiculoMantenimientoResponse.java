package com.sistema_buses.dto.vehiculo;

import java.time.LocalDate;

import com.sistema_buses.enums.GenericoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoMantenimientoResponse {
	private Long id, vehiculoID;
	private String vehiculoPlaca;
	private LocalDate fechaInicio, fechaFin;
	private String descripcion;
	private GenericoEstado estado;
}
