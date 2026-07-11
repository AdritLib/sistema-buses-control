package com.sistema_buses.service;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.sistema_buses.dto.usuario.UsuarioCompletoResponse;
import com.sistema_buses.dto.usuario.UsuarioHabilitacionRequest;
import com.sistema_buses.dto.usuario.UsuarioRequest;
import com.sistema_buses.dto.usuario.UsuarioResponse;
import com.sistema_buses.model.UserDetailsImpl;

public interface UsuarioService {
	UsuarioCompletoResponse obtenerPerfil(@AuthenticationPrincipal UserDetailsImpl detalles);
	List<UsuarioResponse> listar(int pagina, int tamanio);
	List<UsuarioResponse> listarConductores(int pagina, int tamanio);
	List<UsuarioResponse> listarSupervisores(int pagina, int tamanio);
	UsuarioCompletoResponse obtenerPorId(Long id);
	void establecerAcceso(UsuarioHabilitacionRequest request);
	UsuarioResponse actualizar(Long id, UsuarioRequest request);
}
