package com.sistema_buses.dto.incidencia;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IniciarRecorridoRequest {
    @NotNull
    private Long asignacionID;
}
