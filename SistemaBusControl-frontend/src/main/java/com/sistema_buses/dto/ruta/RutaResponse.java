package com.sistema_buses.dto.ruta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutaResponse {
	private Long id;
	private String nombre;
	private String tipo;
	private String estado;
	private Long estacionOrigenId, estacionDestinoId;
	private String estacionOrigenNombre;
	private String estacionDestinoNombre;
}
