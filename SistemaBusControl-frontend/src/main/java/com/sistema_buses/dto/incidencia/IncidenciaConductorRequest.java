package com.sistema_buses.dto.incidencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaConductorRequest {
    private Long recorridoID;
    private String descripcion;
}
