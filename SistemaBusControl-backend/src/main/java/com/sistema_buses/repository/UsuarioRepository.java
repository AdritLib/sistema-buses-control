package com.sistema_buses.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistema_buses.dto.usuario.UsuarioCompletoResponse;
import com.sistema_buses.dto.usuario.UsuarioResponse;
import com.sistema_buses.enums.Roles;
import com.sistema_buses.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByCorreo(String correo);
	
	@Query(
			value = """
					SELECT NEW com.sistema_buses.dto.usuario.UsuarioResponse(
					u.id, 
					u.nombre,
					u.correo, 
					str(u.rol.nombre),
					u.activo) from Usuario u
					""",
			countQuery = "SELECT count(u) FROM Usuario u"
			)
	Page<UsuarioResponse> listar(Pageable page);

	@Query(
			value = """
					SELECT NEW com.sistema_buses.dto.usuario.UsuarioResponse(
					u.id, 
					u.nombre,
					u.correo, 
					str(u.rol.nombre),
					u.activo) FROM Usuario u 
					WHERE u.rol.nombre = :rol AND u.activo = true
					""",
			countQuery = "SELECT count(u) FROM Usuario u"
			)
	Page<UsuarioResponse> listarPorRol(Pageable page, Roles rol);
	
	@Query(
			value = "SELECT NEW com.sistema_buses.dto.usuario.UsuarioCompletoResponse(u.id, u.nombre, u.correo, str(u.rol.nombre), u.telefono, str(u.tipoDocumento), u.numDocumento, u.activo) from Usuario u WHERE u.id = :id"
			)
	Optional<UsuarioCompletoResponse> obtenerPorId(Long id);
	
	@Query("SELECT u.id FROM Usuario u where u.correo = :correo")
	Long obtenerIdUsuarioPorCorreo(String correo);
	
	boolean existsByIdAndRolNombre(Long id, Roles rolNombre);
	boolean existsByIdAndActivoIsTrue(Long id);
}
