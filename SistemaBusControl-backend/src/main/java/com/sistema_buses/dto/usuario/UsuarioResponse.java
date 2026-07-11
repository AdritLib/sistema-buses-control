package com.sistema_buses.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private boolean activo;
}
