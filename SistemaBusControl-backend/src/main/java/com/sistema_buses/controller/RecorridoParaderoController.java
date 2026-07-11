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
import com.sistema_buses.dto.recorrido.RecorridoParaderoRequest;
import com.sistema_buses.dto.recorrido.RecorridoParaderoResponse;
import com.sistema_buses.service.RecorridoParaderoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recorridoparadero")
@RequiredArgsConstructor
@Tag(name = "Recorrido Paradero", description = "Manejo de las relaciones Recorrido y Paradero registradas.")
public class RecorridoParaderoController {
	private final RecorridoParaderoService service;

	@GetMapping
	@HasAdminRol
	public ResponseEntity<List<RecorridoParaderoResponse>> listar(@RequestParam int pagina){
		return ResponseEntity.ok(service.listar(pagina));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RecorridoParaderoResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(service.encontrarPorID(id));
	}
	
	@PostMapping
	public ResponseEntity<RecorridoParaderoResponse> registrar(@RequestBody RecorridoParaderoRequest request){
		return ResponseEntity.ok(service.registrar(request));
	}
	
	@PutMapping("/{id}")
	@HasAdminRol
	public ResponseEntity<RecorridoParaderoResponse> actualizar(@PathVariable Long id, @RequestBody RecorridoParaderoRequest request){
		return ResponseEntity.ok(service.actualizar(id, request));
	}
}
