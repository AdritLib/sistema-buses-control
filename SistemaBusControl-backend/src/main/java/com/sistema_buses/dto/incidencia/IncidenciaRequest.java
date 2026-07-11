package com.sistema_buses.dto.incidencia;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenciaRequest {
	@NotNull
	private Long recorridoID;
	private Long usuarioID;
	
	@NotBlank
    @Size(max = 250)
	private String descripcion;
	
	private LocalDateTime fechaHoraSuceso;
}
