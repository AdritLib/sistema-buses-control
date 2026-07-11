package com.sistema_buses.dto.vehiculo;

import java.time.LocalDate;

import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.validators.FechaMin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoMantenimientoRequest {
	private Long vehiculoID;
	@FechaMin
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String descripcion;
	@NotNull(message = "El campo 'estado' no puede estar vacio")
	private GenericoEstado estado;
}
