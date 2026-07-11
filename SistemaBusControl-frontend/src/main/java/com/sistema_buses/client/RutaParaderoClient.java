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
import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaParaderoRequest;
import com.sistema_buses.dto.ruta.RutaParaderoResponse;

@FeignClient(name = "ruta-paradero-client", url = "${backend.base-url}/api/rutaparadero", configuration = FeignInterceptor.class)
public interface RutaParaderoClient {
	@GetMapping
	public void listar(@RequestParam int pagina);
	
	@GetMapping("/listarPorRuta/{idRuta}")
	public List<ParaderoResponse> listarPorRuta(@PathVariable Long idRuta);
	
	@PostMapping
	public RutaParaderoResponse registrar(@RequestBody RutaParaderoRequest request);
	
	@PutMapping("/{id}")
	public RutaParaderoResponse actualizar(@PathVariable Long id, @RequestBody RutaParaderoRequest request);
}
