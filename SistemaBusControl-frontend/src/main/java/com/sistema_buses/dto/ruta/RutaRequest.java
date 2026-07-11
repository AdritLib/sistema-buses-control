package com.sistema_buses.dto.ruta;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutaRequest {
	private String nombre;
	private String tipo;
	private String estado;
	private Long estacionOrigenId, estacionDestinoId;
	
	private List<Long> paraderosId;
}
