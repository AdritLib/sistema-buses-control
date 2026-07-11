package com.sistema_buses.exception;

public class UsuarioNoEncontradoException extends ErrorDeNegocioException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNoEncontradoException(Long userId) {
        super("Usuario no encontrado con ID: " + userId);
    }
	
	public UsuarioNoEncontradoException(String email) {
		super(email.isBlank() ? "Usuario no encontrado" : "El usuario con correo "+email+" no fue encontrado");
	}
    public UsuarioNoEncontradoException() {
        super("Usuario no encontrado");
    }
}
