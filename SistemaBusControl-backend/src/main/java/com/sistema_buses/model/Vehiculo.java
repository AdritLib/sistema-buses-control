package com.sistema_buses.model;

import com.sistema_buses.enums.VehiculoEstado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 15)
	private String placa;
	
	@Column(nullable = false)
	private VehiculoEstado estado;
	
	@Column(nullable = false, length = 100)
	private String marca;
	
	@Column(nullable = false, length = 100)
	private String modelo;
	
	@Column(nullable = false, length = 4)
	private int year;
	
	@Column(nullable = false, length = 10)
	private int numAsientos;
	
	@OneToOne(optional = true)
	@JoinColumn(name = "vehiculo_seguro_id")
	private VehiculoSeguro seguro;
}
