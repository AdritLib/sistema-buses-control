package com.sistema_buses.exception;

public class ParaderoNoEncontradoException extends ErrorDeNegocioException {
	private static final long serialVersionUID = 1L;
	
	public ParaderoNoEncontradoException(Long paraderoID) {
        super("Paradero no encontrado con ID: " + paraderoID);
    }
}
