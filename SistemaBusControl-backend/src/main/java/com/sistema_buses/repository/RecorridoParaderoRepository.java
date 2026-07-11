package com.sistema_buses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.recorrido.RecorridoParaderoResponse;
import com.sistema_buses.model.RecorridoParadero;

@Repository
public interface RecorridoParaderoRepository extends JpaRepository<RecorridoParadero, Long> {
	Optional<RecorridoParadero> findByRecorridoIdAndRutaParaderoId(Long recorridoID, Long rutaParaderoID);
	List<RecorridoParadero> findByRecorridoIdOrderByRutaParaderoOrdenAsc(Long recorridoID);
	
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.recorrido.RecorridoParaderoResponse(r.id, r.recorrido.id, r.rutaParadero.id, r.observaciones, r.estadoLlegada) FROM RecorridoParadero r",
			countQuery = "SELECT count(r) FROM RecorridoParadero r")
	Page<RecorridoParaderoResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.recorrido.RecorridoParaderoResponse(r.id, r.recorrido.id, r.rutaParadero.id, r.observaciones, r.estadoLlegada) FROM RecorridoParadero r WHERE r.id = :recorridoParaderoID")
	Optional<RecorridoParaderoResponse> encontrarPorID(Long recorridoParaderoID);
}
