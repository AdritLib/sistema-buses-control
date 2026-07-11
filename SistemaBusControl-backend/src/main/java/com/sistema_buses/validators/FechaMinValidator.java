package com.sistema_buses.validators;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FechaMinValidator implements ConstraintValidator<FechaMin, LocalDate> {
	private int dias;
	
	@Override
	public void initialize(FechaMin annotation) {
		dias = annotation.dias();
	}
	
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return true;
        
        LocalDate hoy = LocalDate.now();
        LocalDate minimo = hoy.plusDays(dias);
        
        return !value.isBefore(minimo);
    }
}