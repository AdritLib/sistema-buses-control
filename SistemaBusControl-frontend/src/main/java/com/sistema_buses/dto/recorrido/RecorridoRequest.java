package com.sistema_buses.dto.recorrido;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecorridoRequest {
	private Long asignacionID;
	private LocalTime horaInicio, horaFin;
	private String estado;
}
