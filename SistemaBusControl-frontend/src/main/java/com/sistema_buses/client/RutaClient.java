package com.sistema_buses.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema_buses.config.FeignInterceptor;
import com.sistema_buses.dto.ruta.RutaRequest;
import com.sistema_buses.dto.ruta.RutaResponse;

@FeignClient(name = "ruta-client", url = "${backend.base-url}/api/ruta", configuration = FeignInterceptor.class)
public interface RutaClient {
	@GetMapping
	public List<RutaResponse> listar(@RequestParam int pagina);
	
	@PostMapping
	public RutaResponse registrar(@RequestBody RutaRequest request);
	
	@PutMapping("/{id}")
	public RutaResponse actualizar(@PathVariable Long id, @RequestBody RutaRequest request);
	
	@GetMapping("/{id}")
	public RutaResponse obtenerPorId(@PathVariable Long id);
}
