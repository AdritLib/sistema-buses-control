package com.sistema_buses.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sistema_buses.enums.GenericoEstado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asignaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Asignacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario conductor;
	
	@ManyToOne
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
	
	@ManyToOne
	@JoinColumn(name = "ruta_id")
	private Ruta ruta;
	
	@Column(nullable = false)
	private LocalDate fecha;
	
	@Column(nullable = false)
	private LocalTime horaInicio;
	
	@Column(nullable = true)
	private LocalTime horaFin;
	
	@Enumerated(EnumType.ORDINAL)
	private GenericoEstado estado;
}
