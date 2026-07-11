package com.sistema_buses.dto.vehiculo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoSeguroRequest {
	private String numero;
	private LocalDate fechaVencimiento;
}
