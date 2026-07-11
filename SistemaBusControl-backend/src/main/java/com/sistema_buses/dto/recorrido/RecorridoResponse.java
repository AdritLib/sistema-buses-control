package com.sistema_buses.dto.recorrido;

import java.time.LocalTime;

import com.sistema_buses.enums.RecorridoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecorridoResponse {
	private Long id, asignacionID;
	private LocalTime horaInicio, horaFin;
	private RecorridoEstado estado;
}
