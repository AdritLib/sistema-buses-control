package com.sistema_buses.exception;

public class VehiculoNoEncontradoException extends ErrorDeNegocioException {
	private static final long serialVersionUID = 1L;
	
	public VehiculoNoEncontradoException(Long id) {
        super("Vehiculo no encontrado con ID: " + id);
    }

	public VehiculoNoEncontradoException() {
		super("Vehiculo no encontrado");
	}
}
