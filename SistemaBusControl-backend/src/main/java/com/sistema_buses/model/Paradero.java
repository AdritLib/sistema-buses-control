package com.sistema_buses.model;

import java.math.BigDecimal;

import com.sistema_buses.enums.GenericoEstado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paraderos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paradero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nombre;
	
	@Column(nullable = false, unique = true)
	private String direccion;
	
	@Column(nullable = false)
	private String referencia;
	
	@Column(nullable = false, precision = 10, scale = 8)
	private BigDecimal latitud;
	
	@Column(nullable = false, precision = 11, scale = 8)
	private BigDecimal longitud;
	
	@Enumerated(EnumType.ORDINAL)
	private GenericoEstado estado;
}
