package com.sistema_buses.dto.ruta;

import java.util.List;

import com.sistema_buses.dto.paradero.ParaderoConductorResponse;
import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.RutaTipo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RutaConductorResponse {
    private Long id;
    private String nombre;
    private RutaTipo tipo;
    private GenericoEstado estado;
    private Long estacionOrigenId;
    private String estacionOrigenNombre;
    private Long estacionDestinoId;
    private String estacionDestinoNombre;
    private List<ParaderoConductorResponse> paraderos;
}
