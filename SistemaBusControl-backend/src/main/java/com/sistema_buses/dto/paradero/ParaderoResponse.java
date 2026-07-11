package com.sistema_buses.dto.paradero;

import java.math.BigDecimal;

import com.sistema_buses.enums.GenericoEstado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParaderoResponse {
	private Long id;
	private String nombre, direccion, referencia;
	private BigDecimal latitud;
	private BigDecimal longitud;
	private GenericoEstado estado;
}
