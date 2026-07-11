package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.recorrido.RecorridoResponse;
import com.sistema_buses.enums.RecorridoEstado;
import com.sistema_buses.model.Recorrido;

@Repository
public interface RecorridoRepository extends JpaRepository<Recorrido, Long> {
	Optional<Recorrido> findFirstByAsignacionIdAndEstadoOrderByIdDesc(Long asignacionID, RecorridoEstado estado);
	Optional<Recorrido> findFirstByAsignacionIdOrderByIdDesc(Long asignacionID);
	Optional<Recorrido> findFirstByAsignacionConductorIdAndEstadoOrderByIdDesc(Long conductorID, RecorridoEstado estado);

	Page<Recorrido> findByAsignacionConductorIdOrderByIdDesc(Long conductorID, Pageable pageable);
	
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.recorrido.RecorridoResponse(r.id, r.asignacion.id, r.horaInicio, r.horaFin, r.estado) FROM Recorrido r",
			countQuery = "SELECT count(r) FROM Recorrido r")
	Page<RecorridoResponse> listar(Pageable page);
	
	@Query("SELECT NEW com.sistema_buses.dto.recorrido.RecorridoResponse(r.id, r.asignacion.id, r.horaInicio, r.horaFin, r.estado) FROM Recorrido r WHERE r.id = :recorridoID")
	Optional<RecorridoResponse> encontrarPorID(Long recorridoID);
}
