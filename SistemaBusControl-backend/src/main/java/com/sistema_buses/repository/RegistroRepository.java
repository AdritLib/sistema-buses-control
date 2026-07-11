package com.sistema_buses.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.RegistroResponse;
import com.sistema_buses.model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long>{
	@Query(
			value = """
			SELECT NEW com.sistema_buses.dto.RegistroResponse(r.id, r.accion, r.descripcion, r.entidadAfectada, r.fecha, u.id, u.nombre)
			FROM Registro r INNER JOIN Usuario u ON u.id = r.usuarioID
			""",
			countQuery = "SELECT count(r) FROM Registro r")
	Page<RegistroResponse> listar(Pageable page);
}
