package com.sistema_buses.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioHabilitacionRequest {
    private Long usuarioID;
    private String motivo;
    private boolean activo;
}