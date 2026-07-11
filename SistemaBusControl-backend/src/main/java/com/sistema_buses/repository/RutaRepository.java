package com.sistema_buses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaResponse;
import com.sistema_buses.model.Ruta;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long>{
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.ruta.RutaResponse(r.id, r.nombre, r.tipo, r.estado, r.origen.id, r.destino.id, r.origen.nombre, r.destino.nombre) FROM Ruta r",
			countQuery = "SELECT count(r) FROM Ruta r")
	Page<RutaResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.ruta.RutaResponse(r.id, r.nombre, r.tipo, r.estado, r.origen.id, r.destino.id, r.origen.nombre, r.destino.nombre) FROM Ruta r WHERE r.id = :rutaID")
	Optional<RutaResponse> encontrarPorID(Long rutaID);
	
	@Query("SELECT NEW com.sistema_buses.dto.paradero.ParaderoResponse(p.id, p.nombre, p.direccion, p.referencia, p.latitud, p.longitud, p.estado) FROM RutaParadero rp INNER JOIN Paradero p WHERE rp.ruta.id = :rutaID AND p.id = rp.paradero.id")
	List<ParaderoResponse> listarParaderos(Long rutaID);
	
	@Modifying
	@Query(value = "INSERT INTO rutas_paraderos (paradero_id, ruta_id) VALUES (:paraderoID, :rutaID)", nativeQuery = true)
	int agregarParadero(Long rutaID, Long paraderoID);
	
	@Modifying
	@Query(value = "DELETE FROM rutas_paraderos rp WHERE rp.paradero_id = :paraderoID AND rp.ruta_id = :rutaID", nativeQuery = true)
	int removerParadero(Long rutaID, Long paraderoID);
}
