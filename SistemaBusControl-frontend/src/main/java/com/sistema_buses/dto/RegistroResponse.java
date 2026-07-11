package com.sistema_buses.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroResponse {
	private Long id;
	private String accion;
	private String descripcion;
	private String entidadAfectada;
	private LocalDateTime fecha;
	private Long usuarioId;
	private String usuarioNombre;
}
