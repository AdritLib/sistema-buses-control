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
public class RecorridoParaderoRequest {
	private Long recorridoID, rutaParaderoID;
	private String observaciones;
	private LlegadaEstado estado;
}
