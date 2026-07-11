package com.sistema_buses.dto.usuario;

import com.sistema_buses.enums.Roles;
import com.sistema_buses.enums.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {
    private String nombre, correo, clave, numDocumento, telefono;
    private TipoDocumento tipoDocumento;
    private Roles rol;
}
