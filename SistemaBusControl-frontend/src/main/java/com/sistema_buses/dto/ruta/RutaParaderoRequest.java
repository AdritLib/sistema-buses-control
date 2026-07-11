package com.sistema_buses.dto.ruta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutaParaderoRequest {
	private Long rutaID, paraderoID;
	private int orden;
	private String estado;
}