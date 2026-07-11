package com.sistema_buses.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_buses.config.security.HasConductorOrAdminRol;
import com.sistema_buses.dto.asignacion.AsignacionConductorResponse;
import com.sistema_buses.dto.recorrido.RecorridoConductorResponse;
import com.sistema_buses.dto.ruta.RutaConductorResponse;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.service.ConductorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/conductor")
@HasConductorOrAdminRol
@RequiredArgsConstructor
@Tag(name = "Operación del conductor", description = "Consulta de la operación asignada al conductor autenticado.")
public class ConductorController {
    private final ConductorService conductorService;

    @GetMapping("/{idUsuario}/asignacion-activa")
    @Operation(summary = "Asignación activa o del día")
    public ResponseEntity<AsignacionConductorResponse> obtenerAsignacion(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(conductorService.obtenerAsignacionDelDia(idUsuario));
    }

    @GetMapping("/{idUsuario}/vehiculo-asignado")
    @Operation(summary = "Vehículo asignado al conductor para el día")
    public ResponseEntity<VehiculoResponse> obtenerVehiculo(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(
                conductorService.obtenerAsignacionDelDia(idUsuario).getVehiculo());
    }

    @GetMapping("/{idUsuario}/ruta-hoy")
    @Operation(summary = "Ruta y paraderos ordenados del día")
    public ResponseEntity<RutaConductorResponse> obtenerRuta(
            @PathVariable Long idUsuario) {
        return ResponseEntity.ok(conductorService.obtenerRutaDelDia(idUsuario));
    }

    @GetMapping("/{idUsuario}/historial")
    @Operation(summary = "Historial básico de recorridos")
    public ResponseEntity<List<RecorridoConductorResponse>> obtenerHistorial(
            @PathVariable Long idUsuario,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(conductorService.obtenerHistorial(idUsuario, pagina, size));
    }
}
