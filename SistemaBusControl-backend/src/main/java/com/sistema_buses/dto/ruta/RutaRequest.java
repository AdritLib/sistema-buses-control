package com.sistema_buses.dto.ruta;

import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.RutaTipo;

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
	private RutaTipo tipo;
	private GenericoEstado estado;
	private Long estacionOrigenId, estacionDestinoId;
}
