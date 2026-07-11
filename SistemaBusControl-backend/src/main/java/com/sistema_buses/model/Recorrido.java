package com.sistema_buses.model;

import java.time.LocalTime;
import java.util.Set;

import com.sistema_buses.enums.RecorridoEstado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recorridos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recorrido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "asignacion_id")
	private Asignacion asignacion;
	
	@Column(name = "hora_inicio", nullable = true)
	private LocalTime horaInicio;
	
	@Column(name = "hora_fin", nullable = true)
	private LocalTime horaFin;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 20)
	private RecorridoEstado estado;
	
	@OneToMany(mappedBy = "recorrido")
	private Set<RecorridoParadero> recorridosParaderos;
}
