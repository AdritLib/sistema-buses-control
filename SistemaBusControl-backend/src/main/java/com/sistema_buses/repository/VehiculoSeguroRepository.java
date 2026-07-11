package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.vehiculo.VehiculoSeguroResponse;
import com.sistema_buses.model.VehiculoSeguro;

@Repository
public interface VehiculoSeguroRepository extends JpaRepository<VehiculoSeguro, Long> {
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoSeguroResponse(v.id, v.numero, v.fechaVencimiento) FROM VehiculoSeguro v",
			countQuery = "SELECT count(v) FROM VehiculoSeguro v")
	Page<VehiculoSeguroResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoSeguroResponse(v.id, v.numero, v.fechaVencimiento) FROM VehiculoSeguro v WHERE v.id = :vehiculoSeguroID")
	Optional<VehiculoSeguroResponse> encontrarPorID(Long vehiculoSeguroID);
}
