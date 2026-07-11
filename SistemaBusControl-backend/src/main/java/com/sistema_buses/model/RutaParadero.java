package com.sistema_buses.model;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "rutas_paraderos",
		uniqueConstraints = @UniqueConstraint(columnNames = {"ruta_id", "paradero_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RutaParadero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ruta_id")
	private Ruta ruta;
	
	@ManyToOne
	@JoinColumn(name = "paradero_id")
	private Paradero paradero;
	
	@Column(nullable = false, length = 10)
	private int orden;
	
	@Enumerated(EnumType.ORDINAL)
	private GenericoEstado estado;
}
