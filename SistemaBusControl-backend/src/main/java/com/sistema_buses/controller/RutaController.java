package com.sistema_buses.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.sistema_buses.dto.ruta.RutaRequest;
import com.sistema_buses.dto.ruta.RutaResponse;
import com.sistema_buses.dto.ruta.RutaParaderoRequest;
import com.sistema_buses.service.RutaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ruta")
@RequiredArgsConstructor
@Tag(name = "Rutas", description = "Manejo de las rutas registradas.")
public class RutaController {
	private final RutaService rutaService;
	
	@Operation(summary = "Listar rutas", description = "Devuelve una lista de rutas registradas.")
	@GetMapping
	public ResponseEntity<List<RutaResponse>> listar(@RequestParam int pagina){
		return ResponseEntity.ok(rutaService.listar(pagina));
	}

	@Operation(summary = "Listar paraderos de ruta", description = "Devuelve la lista de paraderos por ruta.")
	@GetMapping("/{id}/paraderos")
	public ResponseEntity<List<ParaderoResponse>> listarParaderos(@PathVariable Long id){
		return ResponseEntity.ok(rutaService.listarParaderos(id));
	}
	
	@Operation(summary = "Obtener ruta por ID", description = "Devuelve la ruta registrada por su ID.")
	@GetMapping("/{id}")
	public ResponseEntity<RutaResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(rutaService.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar ruta", description = "Devuelve la ruta registrada.")
	@PostMapping
	@HasAdminRol
	public ResponseEntity<RutaResponse> registrar(@RequestBody RutaRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(rutaService.registrar(request));
	}
	
	@Operation(summary = "Actualizar ruta", description = "Devuelve la ruta actualizada.")
	@PutMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<RutaResponse> actualizar(@PathVariable Long id, @RequestBody RutaRequest request){
		return ResponseEntity.ok(rutaService.actualizar(id, request));
	}
	
	@Operation(summary = "Eliminar ruta", description = "Elimina la ruta por su ID.")
	@DeleteMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<Void> eliminarPorID(@PathVariable Long id){
		rutaService.eliminarPorID(id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Agregar paradero", description = "Agrega un paradero a una ruta.")
	@PostMapping("/agregarparadero")
	@HasAdminRol
	public ResponseEntity<String> agregarParadero(@RequestBody RutaParaderoRequest request){
		rutaService.agregarParadero(request.getRutaID(), request.getParaderoID());
		return ResponseEntity.ok("Se asigno el paradero a la ruta");
	}
	
	@Operation(summary = "Remover paradero", description = "Remueve un paradero de una ruta.")
	@PostMapping("/removerparadero")
	@HasAdminRol
	public ResponseEntity<String> removerParadero(@RequestBody RutaParaderoRequest request){
		rutaService.removerParadero(request.getRutaID(), request.getParaderoID());
		return ResponseEntity.ok("Se removio el paradero a la ruta");
	}
}
