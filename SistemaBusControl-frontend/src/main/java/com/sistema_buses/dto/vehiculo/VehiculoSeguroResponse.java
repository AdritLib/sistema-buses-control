package com.sistema_buses.dto.vehiculo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoSeguroResponse {

	private Long id;
	private String numero;
	private LocalDate fechaVencimiento;

}