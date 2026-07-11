package com.sistema_buses.dto;

import java.time.LocalDateTime;

import com.sistema_buses.enums.RegistroAccion;

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
	private RegistroAccion accion;
	private String descripcion;
	private String entidadAfectada;
	private LocalDateTime fecha;
	private Long usuarioId;
	private String usuarioNombre;
}
