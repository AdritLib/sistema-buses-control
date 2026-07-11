package com.sistema_buses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.model.Paradero;

@Repository
public interface ParaderoRepository extends JpaRepository<Paradero, Long> {
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.paradero.ParaderoResponse(p.id, p.nombre, p.direccion, p.referencia, p.latitud, p.longitud, p.estado) FROM Paradero p",
			countQuery = "SELECT count(p) FROM Paradero p")
	Page<ParaderoResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.paradero.ParaderoResponse(p.id, p.nombre, p.direccion, p.referencia, p.latitud, p.longitud, p.estado) FROM Paradero p WHERE p.id = :paraderoID"
			)
	Optional<ParaderoResponse> encontrarPorID(Long paraderoID);
	
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.paradero.ParaderoResponse(p.id, p.nombre, p.direccion, p.referencia, p.latitud, p.longitud, p.estado) FROM Paradero p JOIN RutaParadero rp ON p.id = rp.paradero.id WHERE rp.ruta.id = :rutaID",
			countQuery = "SELECT count(p) FROM Paradero p")
	List<ParaderoResponse> listarPorRuta(Long rutaID);
}
