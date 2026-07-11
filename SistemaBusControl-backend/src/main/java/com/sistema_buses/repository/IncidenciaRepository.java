package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.incidencia.IncidenciaResponse;
import com.sistema_buses.model.Incidencia;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
	Page<Incidencia> findByUsuarioIdOrderByFechaHoraSucesoDesc(Long usuarioID, Pageable pageable);
	Page<Incidencia> findAllByOrderByFechaHoraSucesoDesc(Pageable pageable);
	
	@Query("""
		    SELECT new com.sistema_buses.dto.incidencia.IncidenciaResponse(
		        i.id,
		        i.recorrido.id,
		        CONCAT('Asignación ', i.recorrido.asignacion.id),
		        i.usuario.id,
		        u.nombre,
		        i.descripcion,
		        i.fechaHoraSuceso,
		        null,
				null,
				null,
				null
		    )
		    FROM Incidencia i
		    INNER JOIN Usuario u ON i.usuario.id = u.id
		    INNER JOIN Recorrido r ON i.recorrido.id = r.id
		    WHERE i.id = :incidenciaID
		""")
	Page<IncidenciaResponse> listar(Pageable page);
	
	@Query("""
		    SELECT new com.sistema_buses.dto.incidencia.IncidenciaResponse(
		        i.id,
		        i.recorrido.id,
		        CONCAT('Asignación ', i.recorrido.asignacion.id),
		        i.usuario.id,
		        u.nombre,
		        i.descripcion,
		        i.fechaHoraSuceso,
		        null,
				null,
				null,
				null
		    )
		    FROM Incidencia i
		    INNER JOIN Usuario u ON i.usuario.id = u.id
		    INNER JOIN Recorrido r ON i.recorrido.id = r.id
		    WHERE i.id = :incidenciaID
		""")
	Optional<IncidenciaResponse> encontrarPorID(@Param("incidenciaID") Long incidenciaID);
}
