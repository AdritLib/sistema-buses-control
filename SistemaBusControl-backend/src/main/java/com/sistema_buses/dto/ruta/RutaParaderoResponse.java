package com.sistema_buses.dto.ruta;

import com.sistema_buses.enums.GenericoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RutaParaderoResponse {
	private Long id;
	private Long rutaID, paraderoID;
	private int orden;
	private GenericoEstado estado;
}