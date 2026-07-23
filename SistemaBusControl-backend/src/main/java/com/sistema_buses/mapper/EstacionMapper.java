package com.sistema_buses.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.sistema_buses.dto.EstacionRequest;
import com.sistema_buses.dto.EstacionResponse;
import com.sistema_buses.enums.Roles;
import com.sistema_buses.model.Estacion;
import com.sistema_buses.model.Usuario;
import com.sistema_buses.repository.UsuarioRepository;

@Mapper(componentModel = "spring")
public abstract class EstacionMapper {
	@Autowired
	protected UsuarioRepository usuarioRepository;

	@Mapping(source = "supervisor", target = "supervisorId", qualifiedByName = "supervisorId")
	@Mapping(source = "supervisor", target = "supervisorNombre", qualifiedByName = "supervisorNombre")
	public abstract EstacionResponse toResponse(Estacion estacion);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "supervisor", source = "supervisorId", qualifiedByName = "supervisor")
	public abstract Estacion toEntity(EstacionRequest request);
	
	@Named("supervisorNombre")
	protected String supervisorNombre(Usuario usuario) {
		return usuario == null ? null : usuario.getNombre();
	}
	
	@Named("supervisorId")
	protected Long supervisorId(Usuario usuario) {
		return usuario == null ? null : usuario.getId();
	}
	
	@Named("supervisor")
	protected Usuario supervisor(Long supervisorId) {
		if(!usuarioRepository.existsByIdAndActivoIsTrue(supervisorId) || 
				!usuarioRepository.existsByIdAndRolNombre(supervisorId, Roles.SUPERVISOR)) return null;
		return usuarioRepository.findById(supervisorId).orElse(null);
	}
}