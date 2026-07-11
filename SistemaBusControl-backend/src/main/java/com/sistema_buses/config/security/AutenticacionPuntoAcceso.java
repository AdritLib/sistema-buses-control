package com.sistema_buses.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AutenticacionPuntoAcceso implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String jsonResponse = "{"
                + "\"timestamp\": \"" + LocalDateTime.now() + "\","
                + "\"status\": 401,"
                + "\"error\": \"Unauthorized\","
                + "\"message\": \"No estas autenticado para acceder a este recurso.\","
                + "\"path\": \"" + request.getRequestURI() + "\""
                + "}";
        response.getWriter().write(jsonResponse);
	}
	
}
