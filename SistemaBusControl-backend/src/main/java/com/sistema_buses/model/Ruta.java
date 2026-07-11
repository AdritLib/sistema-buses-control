package com.sistema_buses.model;

import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.RutaTipo;

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
@Table(name = "rutas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ruta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nombre;
	
	@Enumerated(EnumType.ORDINAL)
	private RutaTipo tipo;
	
	@ManyToOne
	@JoinColumn(name = "estacion_origen_id")
	private Estacion origen;
	
	@ManyToOne
	@JoinColumn(name = "estacion_destino_id")
	private Estacion destino;
	
	@Enumerated(EnumType.ORDINAL)
	private GenericoEstado estado;

	@Override
	public String toString() {
		return "[id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", origen=" + origen.getId() + ", destino="
				+ destino.getId() + ", estado=" + estado.toString() + "]";
	}
}
