package com.sistema_buses.exception;

public class EstacionNoEncontradoException extends ErrorDeNegocioException {
	private static final long serialVersionUID = 1L;
	
	public EstacionNoEncontradoException(Long estacionID) {
        super("Estacion no encontrada con ID: " + estacionID);
    }
	public EstacionNoEncontradoException() {
        super("Estacion no encontrada");
    }
}
