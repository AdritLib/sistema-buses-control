package com.sistema_buses.service.autenticacion;

import org.springframework.security.core.context.SecurityContextHolder;

import com.sistema_buses.enums.Roles;
import com.sistema_buses.model.UserDetailsImpl;
import com.sistema_buses.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VehiculoAuthorizationServiceImpl {
	private final UsuarioRepository usuarioRepository;
	
	public boolean vehiculoEsDeConductor(Long id) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userDetails == null) return false;
		Long usuarioID = usuarioRepository.obtenerIdUsuarioPorCorreo(userDetails.getUsername());
		if(usuarioRepository.existsByIdAndRolNombre(usuarioID, Roles.ADMIN)) return true;
		if(!usuarioRepository.existsByIdAndRolNombre(usuarioID, Roles.CONDUCTOR)) return false;
		return true;
	}
}
