package com.sistema_buses.exception;

public class ErrorDeNegocioException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ErrorDeNegocioException(String mensaje) {
		super(mensaje);
	}
	
  @Override
    public synchronized Throwable fillInStackTrace() {
        // Prevents the JVM from gathering stack data
	        return this; 
	    }
}
