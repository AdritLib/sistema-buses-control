package com.sistema_buses.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.sistema_buses.enums.TipoDocumento;

import jakarta.persistence.CascadeType;
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
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 150, unique = true)
	private String nombre;
	
	@Column(nullable = true, unique = true)
	private String correo;
	
	@Column(nullable = false, length = 12, unique = true)
	private String telefono;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoDocumento tipoDocumento;
	
	@Column(nullable = false, unique = true, length = 12)
	private String numDocumento;
	
	@Column(nullable = false)
	private String clave;
	
	@CreationTimestamp
	private LocalDate fechaRegistro;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "rol_id")
	private Rol rol;
	
	@Column(nullable = false)
	private boolean activo = true;
     
	 public Usuario(String correo, String nombre, String clave, String telefono, TipoDocumento tipoDocumento, String numDocumento, Rol rol) {
		 this.correo = correo;
		 this.nombre = nombre;
		 this.clave = clave;
		 this.telefono = telefono;
		 this.tipoDocumento = tipoDocumento;
		 this.numDocumento = numDocumento;
		 this.rol = rol;
	 }
	 
	 @Override
	 public String toString() {
		 return "[correo=%s, nombres=%s, telefono=%s, tipoDoc=%s, numDoc=%s, rol=%s]".formatted(
				 correo, 
				 nombre.replaceAll("^(\\S+\\s+\\S{2})\\S+", "$1****"),
				 telefono.replaceAll("^(.{1,3})(.+)$", "$1" + "*".repeat(telefono.length() - 1)), 
				 tipoDocumento.toString(), 
				 numDocumento.replaceAll("^(.{1,3})(.+)$", "$1" + "*".repeat(numDocumento.length() - 1)), 
				 rol.getNombre().toString());
	 }
}
