package com.sistema_buses;
/*
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.sistema_buses.repository.UsuarioRepository;
import com.sistema_buses.service.autenticacion.AuthenticationService;
import com.sistema_buses.service.autenticacion.JwtService;*/

public class JwtServiceTest {
/*@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Spy
	private JwtService jwtService = new JwtService();
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
	@BeforeAll
	static void inicio() {
		authentication = Mockito.mock(Authentication.class);
	}*/
	/*
	@Test
	void deberiaGenerarUnTokenValidoAlIniciarSesion() {
		Rol rol = new Rol(1, Roles.ADMIN, "Usuario Test");
		Usuario datos = new Usuario(1L, "nombreTest", "correo@email.com", "999111222", TipoDocumento.DNI, "80001888", "123456", LocalDate.now(), rol, true);
		Optional<Usuario> usuario = Optional.of(datos);
		
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
		when(usuarioRepository.findByCorreo(anyString())).thenReturn(usuario);
		
		String token = authenticationService.login(new UsuarioLogin(usuario.get().getCorreo(), usuario.get().getClave())).getToken();
		String username = jwtService.extractUsername(token);
		System.out.println(username);
		assertEquals("correo@email.com", username);
	}*/
}
