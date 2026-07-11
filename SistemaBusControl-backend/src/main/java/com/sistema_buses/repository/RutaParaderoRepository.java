package com.sistema_buses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.ruta.RutaParaderoResponse;
import com.sistema_buses.model.RutaParadero;

@Repository
public interface RutaParaderoRepository extends JpaRepository<RutaParadero, Long> {
	List<RutaParadero> findByRutaIdOrderByOrdenAsc(Long rutaID);
	
	@Query(value = "SELECT NEW com.sistema_buses.dto.ruta.RutaParaderoResponse(rp.id, rp.ruta.id, rp.paradero.id, rp.orden, rp.estado) FROM RutaParadero rp",
			countQuery = "SELECT count(rp) FROM RutaParadero rp")
	Page<RutaParaderoResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.ruta.RutaParaderoResponse(rp.id, rp.ruta.id, rp.paradero.id, rp.orden, rp.estado) FROM RutaParadero rp WHERE rp.id = :rutaParaderoID")
	Optional<RutaParaderoResponse> encontrarPorID(Long rutaParaderoID);
}
