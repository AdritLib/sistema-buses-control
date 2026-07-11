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

import com.sistema_buses.dto.recorrido.RecorridoRequest;
import com.sistema_buses.dto.recorrido.RecorridoResponse;
import com.sistema_buses.service.RecorridoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recorrido")
@RequiredArgsConstructor
@Tag(name = "Recorrido", description = "Manejo de los recorridos registrados.")
public class RecorridoController {
	private final RecorridoService recorridoService;

	@GetMapping
	public ResponseEntity<List<RecorridoResponse>> listar(@RequestParam int pagina){
		return ResponseEntity.ok(recorridoService.listar(pagina));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RecorridoResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(recorridoService.encontrarPorID(id));
	}
	
	@PostMapping
	public ResponseEntity<RecorridoResponse> registrar(@RequestBody RecorridoRequest request){
		return ResponseEntity.ok(recorridoService.registrar(request));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RecorridoResponse> actualizar(@PathVariable Long id, @RequestBody RecorridoRequest request){
		return ResponseEntity.ok(recorridoService.actualizar(id, request));
	}
}
