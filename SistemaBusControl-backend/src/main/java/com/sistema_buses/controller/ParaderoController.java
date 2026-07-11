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
import com.sistema_buses.config.security.HasConductorOrAdminRol;
import com.sistema_buses.dto.paradero.ParaderoRequest;
import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.service.ParaderoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paradero")
@RequiredArgsConstructor
@Tag(name = "Paraderos", description = "Manejo de las paraderos disponibles.")
public class ParaderoController {
	private final ParaderoService paraderoService;
	
	@Operation(summary = "Listar paradero", description = "Devuelve una lista de paraderos.")
	@GetMapping
	@HasConductorOrAdminRol
	public ResponseEntity<List<ParaderoResponse>> listar(@RequestParam int pagina, @RequestParam int size){
		return ResponseEntity.ok(paraderoService.listar(pagina, size));
	}
	
	@Operation(summary = "Obtener paradero por ID", description = "Devuelve un paradero por su ID.")
	@GetMapping("/{id}")
	@HasConductorOrAdminRol
	public ResponseEntity<ParaderoResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(paraderoService.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar paradero", description = "Devuelve el paradero registrado.")
	@PostMapping
	@HasAdminRol
	public ResponseEntity<ParaderoResponse> registrar(@RequestBody ParaderoRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(paraderoService.registrar(request));
	}
	
	@Operation(summary = "Actualizar paradero", description = "Devuelve el paradero actualizado.")
	@PutMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<ParaderoResponse> actualizar(@PathVariable Long id, @RequestBody ParaderoRequest request){
		return ResponseEntity.ok(paraderoService.actualizar(id, request));
	}
	
	@Operation(summary = "Eliminar paradero", description = "Elimina un paradero por su ID.")
	@DeleteMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<Void> eliminarPorID(@PathVariable Long id){
		paraderoService.eliminarPorID(id);
		return ResponseEntity.ok().build();
	}
}
