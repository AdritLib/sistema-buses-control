package com.sistema_buses.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehiculo_seguros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoSeguro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 40)
	private String numero;

	@Column(nullable = false)
	private LocalDate fechaVencimiento;
	
	@Override
	public String toString() {
		return "[id="+id+", numero="+numero+", fechaVencimiento="+fechaVencimiento+"]";
	}
}
