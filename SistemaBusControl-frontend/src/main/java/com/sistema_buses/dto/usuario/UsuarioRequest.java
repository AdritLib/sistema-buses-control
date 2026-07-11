package com.sistema_buses.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
	private String nombre, correo, clave, numDocumento, telefono, tipoDocumento;
    private String rol;
}