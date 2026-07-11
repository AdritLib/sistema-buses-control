package com.sistema_buses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sistema_buses.config.security.HasSupervisorOrAdminRol;
import com.sistema_buses.dto.vehiculo.VehiculoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoResponse;
import com.sistema_buses.dto.vehiculo.VehiculoSeguroRequest;
import com.sistema_buses.dto.vehiculo.VehiculoSeguroResponse;
import com.sistema_buses.service.VehiculoMantenimientoService;
import com.sistema_buses.service.VehiculoSeguroService;
import com.sistema_buses.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehiculo")
@Tag(name = "Vehiculos", description = "Manejo de los vehiculos registrados.")
public class VehiculoController {
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private VehiculoMantenimientoService vehiculoMantenimientoservice;
	
	@Autowired
	private VehiculoSeguroService vehiculoSeguroService;
	
	@Operation(summary = "Listar vehiculos", description = "Devuelve una lista de los vehiculos registrados.")
	@GetMapping
	@HasSupervisorOrAdminRol
	public ResponseEntity<List<VehiculoResponse>> listar(@RequestParam int pagina){
		return ResponseEntity.ok(vehiculoService.listar(pagina));
	}
	
	@Operation(summary = "Obtener vehiculo por ID", description = "Devuelve el vehiculo registrado por su ID.")
	@GetMapping("/{id}")
	public ResponseEntity<VehiculoResponse> encontrarPorID(@PathVariable Long id){
		return ResponseEntity.ok(vehiculoService.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar vehiculo", description = "Devuelve el vehiculo registrado.")
	@PostMapping
	@HasSupervisorOrAdminRol
	public ResponseEntity<VehiculoResponse> registrar(@RequestBody VehiculoRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.registrar(request));
	}
	
	@Operation(summary = "Actualizar vehiculo", description = "Devuelve el vehiculo actualizado.")
	@PutMapping("/{id}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<VehiculoResponse> actualizar(@PathVariable Long id, @RequestBody VehiculoRequest request){
		return ResponseEntity.ok(vehiculoService.actualizar(id, request));
	}
	
	@Operation(summary = "Eliminar vehiculo", description = "Elimina el vehiculo por su ID.")
	@DeleteMapping("/{id}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<Void> eliminarPorID(@PathVariable Long id){
		vehiculoService.eliminarPorID(id);
		return ResponseEntity.ok().build();
	}
	
	/* === Mantenimiento === */
	
	@Operation(summary = "Listar mantenimiento", description = "Devuelve una lista de los mantenimientos registrados.")
	@GetMapping("/mantenimiento")
	@HasSupervisorOrAdminRol
	public ResponseEntity<List<VehiculoMantenimientoResponse>> listarVehiculoMantenimiento(@RequestParam int pagina){
		return ResponseEntity.ok(vehiculoMantenimientoservice.listar(pagina));
	}
	
	@Operation(summary = "Obtener mantenimiento por ID", description = "Devuelve el mantenimiento por su ID.")
	@GetMapping("/mantenimiento/{id}")
	public ResponseEntity<VehiculoMantenimientoResponse> encontrarVehiculoMantenimientoPorId(@PathVariable Long id){
		return ResponseEntity.ok(vehiculoMantenimientoservice.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar mantenimiento", description = "Devuelve el mantenimiento registrado.")
	@PostMapping("/mantenimiento")
	@HasSupervisorOrAdminRol
	public ResponseEntity<VehiculoMantenimientoResponse> registrarVehiculoMantenimiento(@RequestBody @Valid VehiculoMantenimientoRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoMantenimientoservice.registrar(request));
	}
	
	@Operation(summary = "Actualizar mantenimiento", description = "Devuelve el mantenimiento actualizado.")
	@PutMapping("/mantenimiento/{id}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<VehiculoMantenimientoResponse> actualizarVehiculoMantenimiento(@PathVariable Long id, @RequestBody VehiculoMantenimientoRequest request){
		return ResponseEntity.ok(vehiculoMantenimientoservice.actualizar(id, request));
	}
	
	@Operation(summary = "Eliminar mantenimiento")
	@DeleteMapping("/mantenimiento/{id}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<Void> eliminarVehiculoMantenimiento(@PathVariable Long id) {
		vehiculoMantenimientoservice.eliminar(id);
		return ResponseEntity.noContent().build();
	}
	
	/* === Vehiculo Seguro === */
	
	@Operation(summary = "Listar seguros", description = "Devuelve una lista de los seguros registrados.")
	@GetMapping("/seguro")
	@HasSupervisorOrAdminRol
	public ResponseEntity<List<VehiculoSeguroResponse>> listarVehiculoSeguro(@RequestParam int pagina){
		return ResponseEntity.ok(vehiculoSeguroService.listar(pagina));
	}
	
	@Operation(summary = "Obtener seguro por ID", description = "Devuelve el seguro registrado por su ID.")
	@GetMapping("/seguro/{id}")
	public ResponseEntity<VehiculoSeguroResponse> encontrarVehiculoSeguroPorId(@PathVariable Long id){
		return ResponseEntity.ok(vehiculoSeguroService.encontrarPorID(id));
	}
	
	@Operation(summary = "Registrar seguro", description = "Devuelve el seguro registrado.")
	@PostMapping("/seguro")
	@HasSupervisorOrAdminRol
	public ResponseEntity<VehiculoSeguroResponse> registrarVehiculoSeguro(@RequestBody VehiculoSeguroRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoSeguroService.registrar(request));
	}
	
	@Operation(summary = "Actualizar seguro", description = "Devuelve el seguro actualizado.")
	@PutMapping("/seguro/{id}")
	@HasSupervisorOrAdminRol
	public ResponseEntity<VehiculoSeguroResponse> actualizarVehiculoSeguro(@PathVariable Long id, @RequestBody VehiculoSeguroRequest request){
		return ResponseEntity.ok(vehiculoSeguroService.actualizar(id, request));
	}
}
