package com.sistema_buses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_buses.config.security.HasAdminRol;
import com.sistema_buses.config.security.HasSupervisorOrAdminRol;
import com.sistema_buses.dto.EstacionRequest;
import com.sistema_buses.dto.EstacionResponse;
import com.sistema_buses.service.EstacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/estacion")
@Tag(name = "Estaciones", description = "Manejo de las estaciones registradas.")
public class EstacionController {
	@Autowired
	private EstacionService estacionService;
	
	@Operation(summary = "Listar estaciones", description = "Devuelve las estaciones registradas.")
	@GetMapping
	@HasSupervisorOrAdminRol
	public ResponseEntity<List<EstacionResponse>> listar(@RequestParam int pagina, @RequestParam(defaultValue = "10") int size){
		return ResponseEntity.ok(estacionService.listar(pagina, size));
	}
	
	@Operation(summary = "Obtener estacion por ID", description = "Devuelve una estacion por su ID.")
	@GetMapping("/{id}")
	public ResponseEntity<EstacionResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(estacionService.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar nueva estacion", description = "Devuelve la estacion registrada.")
	@PostMapping
	@HasAdminRol
	public ResponseEntity<EstacionResponse> registrar(@RequestBody EstacionRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(estacionService.registrar(request));
	}
	
	@Operation(summary = "Actualizar estacion", description = "Devuelve la estacion actualizada.")
	@PutMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<EstacionResponse> actualizar(@PathVariable Long id, @RequestBody EstacionRequest request){
		return ResponseEntity.ok(estacionService.actualizar(id, request));
	}
}
