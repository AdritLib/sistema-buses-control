package com.sistema_buses.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechaMinValidator.class)
@Documented
public @interface FechaMin {
	int dias() default 3;
    String message() default "La fecha debe ser al menos 3 días después de hoy.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}