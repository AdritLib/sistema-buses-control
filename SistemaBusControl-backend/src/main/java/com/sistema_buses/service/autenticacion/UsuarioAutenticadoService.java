package com.sistema_buses.service.autenticacion;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sistema_buses.enums.Roles;
import com.sistema_buses.model.UserDetailsImpl;

@Service
public class UsuarioAutenticadoService {

    public UserDetailsImpl obtenerDetalles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl details)) {
            throw new AccessDeniedException("No se pudo identificar al usuario autenticado.");
        }
        return details;
    }

    public Long obtenerUsuarioID() {
        return obtenerDetalles().getUser().getId();
    }

    public boolean esAdmin() {
        return obtenerDetalles().getUser().getRol().getNombre() == Roles.ADMIN;
    }

    public void validarMismoUsuarioOAdmin(Long usuarioID) {
        if (!esAdmin() && !obtenerUsuarioID().equals(usuarioID)) {
            throw new AccessDeniedException("No puedes consultar la operación de otro conductor.");
        }
    }
}
