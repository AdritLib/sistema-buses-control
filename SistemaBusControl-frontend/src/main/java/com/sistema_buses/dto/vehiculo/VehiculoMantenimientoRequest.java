package com.sistema_buses.dto.vehiculo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoMantenimientoRequest {
	private Long vehiculoID;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicio, fechaFin;
	private String descripcion;
	private String estado;
}