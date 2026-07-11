package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{
	@Query(value = "SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoResponse(v.id, v.placa, v.marca, v.modelo, v.year, v.numAsientos, v.estado) FROM Vehiculo v",
			countQuery = "SELECT count(v) FROM Vehiculo v")
	Page<VehiculoResponse> listar(Pageable page); 
	
	@Query("SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoResponse(v.id, v.placa, v.marca, v.modelo, v.year, v.numAsientos, v.estado) FROM Vehiculo v WHERE v.id = :vehiculoID")
	Optional<VehiculoResponse> encontrarPorID(Long vehiculoID);
	
	@Query("SELECT NEW com.sistema_buses.dto.vehiculo.VehiculoResponse(v.id, v.placa, v.marca, v.modelo, v.year, v.numAsientos, v.estado) FROM Vehiculo v WHERE v.placa = :placa")
	Optional<VehiculoResponse> encontrarPorPlaca(String placa);
}
