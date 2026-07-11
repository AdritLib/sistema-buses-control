package com.sistema_buses.exception;

public class RolNoEncontradoException extends ErrorDeNegocioException {
	private static final long serialVersionUID = 1L;
	
	public RolNoEncontradoException() {
        super("Rol no encontrado");
    }
}
