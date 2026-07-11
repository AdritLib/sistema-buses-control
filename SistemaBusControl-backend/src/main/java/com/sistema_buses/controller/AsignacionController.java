package com.sistema_buses.controller;

import java.util.List;

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

import com.sistema_buses.config.security.HasSupervisorOrAdminRol;
import com.sistema_buses.dto.asignacion.AsignacionRequest;
import com.sistema_buses.dto.asignacion.AsignacionResponse;
import com.sistema_buses.service.AsignacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asignacion")
@RequiredArgsConstructor
@Tag(name = "Asignaciones", description = "Manejo de las asignaciones registradas.")
public class AsignacionController {
	private final AsignacionService service;
	
	@Operation(summary = "Listar asignaciones", description = "Devuelve una lista de asignaciones.")
	@GetMapping
	@HasSupervisorOrAdminRol
	public ResponseEntity<List<AsignacionResponse>> listar(@RequestParam int pagina){
		return ResponseEntity.ok(service.listar(pagina));
	}
	
	@Operation(summary = "Obtener asignacion por ID", description = "Devuelve una asignacion por ID.")
	@GetMapping("/{id}")
	public ResponseEntity<AsignacionResponse> obtenerPorID(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerPorID(id));
	}

	@Operation(summary = "Registrar asignacion", description = "Devuelve la asignacion registrada.")
	@PostMapping
	@HasSupervisorOrAdminRol
	public ResponseEntity<AsignacionResponse> registrar(@RequestBody AsignacionRequest request){
		return ResponseEntity.ok(service.registrar(request));
	}
	
	@Operation(summary = "Actualizar asignacion", description = "Devuelve la asignacion actualizada.")
	@PutMapping("/{id}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<AsignacionResponse> actualizar(@PathVariable Long id, @RequestBody AsignacionRequest request){
		return ResponseEntity.ok(service.actualizar(id, request));
	}
	
	@Operation(summary = "Asginar conductor a asignacion", description = "Asigna un conductor a una asignacion por su ID.")
	@PostMapping("/{id}/asignarconductor/{conductorID}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<String> asginarConductor(@PathVariable Long id, @PathVariable Long conductorID){
		service.asignarConductor(id, conductorID);
		return ResponseEntity.ok("Se asigno un conductor al vehiculo "+id);
	}
	
	@Operation(summary = "Asignar ruta a asignacion", description = "Asigna una ruta a una asignacion por su ID.")
	@PostMapping("/{id}/asignarruta/{rutaID}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<String> asginarRuta(@PathVariable Long id, @PathVariable Long rutaID){
		service.asignarRuta(id, rutaID);
		return ResponseEntity.ok("Se asigno la ruta "+rutaID+" al vehiculo "+id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		service.eliminarPorID(id);
		return ResponseEntity.noContent().build();
	}
}
