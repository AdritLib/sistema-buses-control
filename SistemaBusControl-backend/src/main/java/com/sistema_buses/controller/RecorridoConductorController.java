package com.sistema_buses.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_buses.config.security.HasConductorOrAdminRol;
import com.sistema_buses.dto.incidencia.IniciarRecorridoRequest;
import com.sistema_buses.dto.paradero.ParaderoConductorResponse;
import com.sistema_buses.dto.paradero.ParaderoRecorridoConductorResponse;
import com.sistema_buses.dto.recorrido.MarcarLlegadaRequest;
import com.sistema_buses.dto.recorrido.RecorridoConductorResponse;
import com.sistema_buses.service.RecorridoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recorridos")
@HasConductorOrAdminRol
@RequiredArgsConstructor
@Tag(name = "Recorridos del conductor", description = "Ciclo operativo del recorrido asignado.")
public class RecorridoConductorController {
    private final RecorridoService recorridoService;

    @GetMapping("/{idRecorrido}")
    @Operation(summary = "Detalle del recorrido del conductor")
    public ResponseEntity<RecorridoConductorResponse> obtenerDetalle(
            @PathVariable Long idRecorrido) {
        return ResponseEntity.ok(recorridoService.obtenerDetalleParaConductor(idRecorrido));
    }

    @GetMapping("/conductor/{idUsuario}/activo")
    @Operation(summary = "Recorrido activo del conductor")
    public ResponseEntity<RecorridoConductorResponse> obtenerActivo(
            @PathVariable Long idUsuario) {
        return recorridoService.obtenerActivoParaConductor(idUsuario)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/asignacion/{idAsignacion}/ultimo")
    @Operation(summary = "Último recorrido de una asignación")
    public ResponseEntity<RecorridoConductorResponse> obtenerUltimoPorAsignacion(
            @PathVariable Long idAsignacion) {
        return recorridoService.obtenerUltimoParaAsignacion(idAsignacion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/{idRecorrido}/paraderos")
    @Operation(summary = "Paraderos del recorrido con estado de llegada")
    public ResponseEntity<List<ParaderoRecorridoConductorResponse>> listarParaderos(
            @PathVariable Long idRecorrido) {
        return ResponseEntity.ok(recorridoService.listarDetalleParaderosDelRecorrido(idRecorrido));
    }

    @PostMapping("/iniciar")
    @Operation(summary = "Iniciar recorrido")
    public ResponseEntity<RecorridoConductorResponse> iniciar(
            @Valid @RequestBody IniciarRecorridoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recorridoService.iniciarParaConductor(request.getAsignacionID()));
    }

    @PostMapping("/{idRecorrido}/paraderos/{idRutaParadero}/llegada")
    @Operation(summary = "Marcar llegada a un paradero")
    public ResponseEntity<ParaderoConductorResponse> marcarLlegada(
            @PathVariable Long idRecorrido,
            @PathVariable Long idRutaParadero,
            @Valid @RequestBody(required = false) MarcarLlegadaRequest request) {
        return ResponseEntity.ok(
                recorridoService.marcarLlegada(idRecorrido, idRutaParadero, request));
    }

    @PostMapping("/{idRecorrido}/finalizar")
    @Operation(summary = "Finalizar recorrido")
    public ResponseEntity<RecorridoConductorResponse> finalizar(
            @PathVariable Long idRecorrido) {
        return ResponseEntity.ok(recorridoService.finalizarParaConductor(idRecorrido));
    }
}
