package com.sistema_buses.controller;

import java.util.List;

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
import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaParaderoRequest;
import com.sistema_buses.dto.ruta.RutaParaderoResponse;
import com.sistema_buses.service.RutaParaderoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rutaparadero")
@RequiredArgsConstructor
@Tag(name = "Ruta Paradero", description = "Manejo de las relaciones Ruta y Paradero registradas.")
public class RutaParaderoController {
	private final RutaParaderoService service;

	@Operation(summary = "Listar ruta paradero", description = "Devuelve una lista de ruta paradero.")
	@GetMapping
	public ResponseEntity<List<RutaParaderoResponse>> listar(@RequestParam int pagina){
		return ResponseEntity.ok(service.listar(pagina));
	}
	
	@GetMapping("/listarPorRuta/{idRuta}")
	public ResponseEntity<List<ParaderoResponse>> listarPorRuta(@PathVariable Long idRuta){
		return ResponseEntity.ok(service.listarPorRuta(idRuta));
	}
	
	@Operation(summary = "Obtener ruta paradero por ID", description = "Devuelve una relacion ruta paradero.")
	@GetMapping("/{id}")
	public ResponseEntity<RutaParaderoResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(service.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar ruta paradero", description = "Devuelve la ruta paradero registrada.")
	@PostMapping
	@HasAdminRol
	public ResponseEntity<RutaParaderoResponse> registrar(@RequestBody RutaParaderoRequest request){
		return ResponseEntity.ok(service.registrar(request));
	}
	
	@Operation(summary = "Actualizar ruta paradero", description = "Devuelve la ruta paradero actualizada.")
	@PutMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<RutaParaderoResponse> actualizar(@PathVariable Long id, @RequestBody RutaParaderoRequest request){
		return ResponseEntity.ok(service.actualizar(id, request));
	}
}
