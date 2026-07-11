package com.sistema_buses.dto.recorrido;

import com.sistema_buses.enums.LlegadaEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecorridoParaderoResponse {
	private Long id, recorridoID, rutaParaderoID;
	private String observaciones;
	private LlegadaEstado estado;
}
