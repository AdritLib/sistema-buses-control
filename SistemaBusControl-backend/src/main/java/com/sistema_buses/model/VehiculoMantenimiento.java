package com.sistema_buses.model;

import java.time.LocalDate;

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
@Table(name = "vehiculo_mantenimiento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoMantenimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
	
	@Column(nullable = false)
	private LocalDate fechaInicio;
	
	@Column(nullable = false)
	private LocalDate fechaFin;

	@Column(nullable = false)
	private String descripcion;
	
	@Enumerated(EnumType.ORDINAL)
	private GenericoEstado estado;

	@Override
	public String toString() {
		return "[id=" + id + ", vehiculoId=" + vehiculo.getId() + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", descripcion=" + descripcion + ", estado=" + estado.toString()+ "]";
	}
}
