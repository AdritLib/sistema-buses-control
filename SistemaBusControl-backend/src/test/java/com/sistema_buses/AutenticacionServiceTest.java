package com.sistema_buses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.sistema_buses.dto.usuario.UsuarioLogin;
import com.sistema_buses.exception.UsuarioNoEncontradoException;
import com.sistema_buses.repository.UsuarioRepository;
import com.sistema_buses.service.autenticacion.AuthenticationService;
import com.sistema_buses.service.autenticacion.JwtService;

@ExtendWith(MockitoExtension.class)
public class AutenticacionServiceTest {
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private JwtService jwtService;
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
	private static Authentication authentication;
	
	@BeforeAll
	static void inicio() {
		authentication = Mockito.mock(Authentication.class);
	}
	
	@Test
	void deberiaLanzarExcepcionAlNoEncontrarUsuarioSinCorreoAlIniciarSesion() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
		when(usuarioRepository.findByCorreo(any())).thenReturn(Optional.empty());
		
		UsuarioNoEncontradoException exception = assertThrows(UsuarioNoEncontradoException.class, () -> {
			authenticationService.login(new UsuarioLogin("", ""));
		});
		assertEquals("Usuario no encontrado", exception.getMessage());
	}
}
