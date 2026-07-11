package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoResponse;
import com.sistema_buses.model.VehiculoMantenimiento;

@Repository
public interface VehiculoMantenimientoRepository extends JpaRepository<VehiculoMantenimiento, Long> {
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoMantenimientoResponse(v.id, v.vehiculo.id, v.vehiculo.placa, v.fechaInicio, v.fechaFin, v.descripcion, v.estado) FROM VehiculoMantenimiento v",
			countQuery = "SELECT count(v) FROM VehiculoSeguro v")
	Page<VehiculoMantenimientoResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoMantenimientoResponse(v.id, v.vehiculo.id, v.vehiculo.placa, v.fechaInicio, v.fechaFin, v.descripcion, v.estado) FROM VehiculoMantenimiento v WHERE v.id = :vehiculoMantenimientoID")
	Optional<VehiculoMantenimientoResponse> encontrarPorID(Long vehiculoMantenimientoID);
}
