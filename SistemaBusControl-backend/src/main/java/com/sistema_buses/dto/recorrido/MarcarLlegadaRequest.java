package com.sistema_buses.dto.recorrido;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcarLlegadaRequest {
    @Size(max = 500)
    private String observaciones;
}
