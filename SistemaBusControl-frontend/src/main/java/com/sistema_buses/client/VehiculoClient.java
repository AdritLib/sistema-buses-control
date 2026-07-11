package com.sistema_buses.client;

import com.sistema_buses.config.FeignInterceptor;
import com.sistema_buses.dto.vehiculo.VehiculoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "vehiculo-client", url = "${backend.base-url}/api/vehiculo", configuration = FeignInterceptor.class)
public interface VehiculoClient {

    @GetMapping
    List<VehiculoResponse> listarVehiculos(
            @RequestParam("pagina") int pagina
    );
    
    @PostMapping
    VehiculoResponse registrarVehiculo(
            @RequestBody VehiculoRequest request
    );
    
    @GetMapping("/{id}")
    VehiculoResponse obtenerVehiculoPorId(
            @PathVariable("id") Long id
    );
    
    @GetMapping("/{placa}")
    VehiculoResponse obtenerPorPlaca(
    		@PathVariable String placa
    );

    @PutMapping("/{id}")
    VehiculoResponse actualizarVehiculo(
            @PathVariable("id") Long id,
            @RequestBody VehiculoRequest request
    );
    
    @DeleteMapping("/{id}")
    void eliminarVehiculo(
            @PathVariable("id") Long id
    );
}