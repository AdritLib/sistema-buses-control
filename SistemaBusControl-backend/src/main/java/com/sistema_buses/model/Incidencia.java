package com.sistema_buses.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "incidencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Incidencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "recorrido_id")
	private Recorrido recorrido;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Column(nullable = true)
	private String descripcion;
	
	@Column(nullable = false)
	private LocalDateTime fechaHoraSuceso;

	@Override
	public String toString() {
		return "[id=" + id + ", recorrido=" + recorrido.getId() + ", reportante=" + usuario.getId() + ", descripcion="
				+ descripcion + ", fechaHoraSuceso=" + fechaHoraSuceso.toString() + "]";
	}
}
