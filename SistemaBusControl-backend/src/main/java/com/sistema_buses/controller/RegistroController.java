package com.sistema_buses.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_buses.config.security.HasAdminRol;
import com.sistema_buses.dto.RegistroResponse;
import com.sistema_buses.service.RegistroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/registro")
@HasAdminRol
@RequiredArgsConstructor
@Tag(name = "Registros", description = "Manejo de las acciones del sistema.")
public class RegistroController {
	private final RegistroService registroService;

	@Operation(summary = "Listar registros", description = "Lista las acciones registradas")
	@GetMapping
	public ResponseEntity<List<RegistroResponse>> listar(@RequestParam int pagina, @RequestParam int tamanio){
		return ResponseEntity.ok(registroService.listar(pagina, tamanio));
	}
}
