package com.sistema_buses.model;

import java.time.LocalDateTime;

import com.sistema_buses.enums.LlegadaEstado;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	name = "recorridos_paraderos",
	uniqueConstraints = @UniqueConstraint(columnNames = {"recorrido_id", "ruta_paradero_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecorridoParadero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "recorrido_id")
	private Recorrido recorrido;
	
	@ManyToOne
	@JoinColumn(name = "ruta_paradero_id")
	private RutaParadero rutaParadero;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_llegada", nullable = false, length = 20)
	private LlegadaEstado estadoLlegada;
	
	@Column(nullable = true, length = 250)
	private String observaciones;
	
	@Column(name = "hora_llegada_real", nullable = true)
	private LocalDateTime fechaHoraLlegada;
}
