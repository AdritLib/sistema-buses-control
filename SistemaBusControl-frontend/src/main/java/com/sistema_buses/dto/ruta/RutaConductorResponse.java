package com.sistema_buses.dto.ruta;

import java.util.List;

import com.sistema_buses.dto.paradero.ParaderoConductorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaConductorResponse {
    private Long id;
    private String nombre;
    private String tipo;
    private String estado;
    private Long estacionOrigenId;
    private String estacionOrigenNombre;
    private Long estacionDestinoId;
    private String estacionDestinoNombre;
    private List<ParaderoConductorResponse> paraderos;
}
