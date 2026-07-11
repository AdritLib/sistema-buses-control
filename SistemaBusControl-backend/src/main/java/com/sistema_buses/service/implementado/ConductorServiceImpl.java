package com.sistema_buses.service.implementado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.dto.asignacion.AsignacionConductorResponse;
import com.sistema_buses.dto.paradero.ParaderoConductorResponse;
import com.sistema_buses.dto.recorrido.RecorridoConductorResponse;
import com.sistema_buses.dto.ruta.RutaConductorResponse;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.Roles;
import com.sistema_buses.exception.ErrorDeNegocioException;
import com.sistema_buses.model.Asignacion;
import com.sistema_buses.model.Paradero;
import com.sistema_buses.model.Recorrido;
import com.sistema_buses.model.Ruta;
import com.sistema_buses.model.RutaParadero;
import com.sistema_buses.model.Vehiculo;
import com.sistema_buses.repository.AsignacionRepository;
import com.sistema_buses.repository.RecorridoRepository;
import com.sistema_buses.repository.RutaParaderoRepository;
import com.sistema_buses.repository.UsuarioRepository;
import com.sistema_buses.service.ConductorService;
import com.sistema_buses.service.autenticacion.UsuarioAutenticadoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConductorServiceImpl implements ConductorService {
    private final AsignacionRepository asignacionRepository;
    private final RutaParaderoRepository rutaParaderoRepository;
    private final RecorridoRepository recorridoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    @Override
    @Transactional(readOnly = true)
    public AsignacionConductorResponse obtenerAsignacionDelDia(Long usuarioID) {
        validarConductorConsultado(usuarioID);
        Asignacion asignacion = buscarAsignacionDelDia(usuarioID);
        return mapearAsignacion(asignacion);
    }

    @Override
    @Transactional(readOnly = true)
    public RutaConductorResponse obtenerRutaDelDia(Long usuarioID) {
        validarConductorConsultado(usuarioID);
        return mapearRuta(buscarAsignacionDelDia(usuarioID).getRuta());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecorridoConductorResponse> obtenerHistorial(Long usuarioID, int pagina, int size) {
        validarConductorConsultado(usuarioID);
        int paginaSegura = Math.max(pagina, 0);
        int sizeSeguro = Math.min(Math.max(size, 1), 100);
        return recorridoRepository
                .findByAsignacionConductorIdOrderByIdDesc(usuarioID, PageRequest.of(paginaSegura, sizeSeguro))
                .stream()
                .map(this::mapearRecorrido)
                .toList();
    }

    private void validarConductorConsultado(Long usuarioID) {
        usuarioAutenticadoService.validarMismoUsuarioOAdmin(usuarioID);
        if (!usuarioRepository.existsByIdAndRolNombre(usuarioID, Roles.CONDUCTOR)) {
            throw new ErrorDeNegocioException("El usuario indicado no tiene rol CONDUCTOR.");
        }
    }

    private Asignacion buscarAsignacionDelDia(Long usuarioID) {
        List<Asignacion> asignaciones = asignacionRepository
                .findByConductorIdAndFechaAndEstadoOrderByHoraInicioAsc(
                        usuarioID,
                        LocalDate.now(),
                        GenericoEstado.ACTIVO);
        if (asignaciones.isEmpty()) {
            throw new ErrorDeNegocioException("No tienes un servicio asignado actualmente.");
        }

        LocalTime ahora = LocalTime.now();
        return asignaciones.stream()
                .filter(a -> estaDentroDelHorario(a, ahora))
                .findFirst()
                .orElseGet(() -> asignaciones.stream()
                        .filter(a -> a.getHoraInicio() != null && a.getHoraInicio().isAfter(ahora))
                        .findFirst()
                        .orElse(asignaciones.get(asignaciones.size() - 1)));
    }

    private boolean estaDentroDelHorario(Asignacion asignacion, LocalTime hora) {
        return asignacion.getHoraInicio() != null
                && asignacion.getHoraFin() != null
                && !hora.isBefore(asignacion.getHoraInicio())
                && !hora.isAfter(asignacion.getHoraFin());
    }

    private AsignacionConductorResponse mapearAsignacion(Asignacion asignacion) {
        Vehiculo vehiculo = asignacion.getVehiculo();
        VehiculoResponse vehiculoResponse = VehiculoResponse.builder()
                .id(vehiculo.getId())
                .placa(vehiculo.getPlaca())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .year(vehiculo.getYear())
                .numAsientos(vehiculo.getNumAsientos())
                .estado(vehiculo.getEstado())
                .build();

        return AsignacionConductorResponse.builder()
                .id(asignacion.getId())
                .fecha(asignacion.getFecha())
                .horaInicio(asignacion.getHoraInicio())
                .horaFin(asignacion.getHoraFin())
                .estado(asignacion.getEstado())
                .vehiculo(vehiculoResponse)
                .ruta(mapearRuta(asignacion.getRuta()))
                .build();
    }

    private RutaConductorResponse mapearRuta(Ruta ruta) {
        List<ParaderoConductorResponse> paraderos = rutaParaderoRepository
                .findByRutaIdOrderByOrdenAsc(ruta.getId())
                .stream()
                .map(this::mapearParadero)
                .toList();

        return RutaConductorResponse.builder()
                .id(ruta.getId())
                .nombre(ruta.getNombre())
                .tipo(ruta.getTipo())
                .estado(ruta.getEstado())
                .estacionOrigenId(ruta.getOrigen() != null ? ruta.getOrigen().getId() : null)
                .estacionOrigenNombre(ruta.getOrigen() != null ? ruta.getOrigen().getNombre() : null)
                .estacionDestinoId(ruta.getDestino() != null ? ruta.getDestino().getId() : null)
                .estacionDestinoNombre(ruta.getDestino() != null ? ruta.getDestino().getNombre() : null)
                .paraderos(paraderos)
                .build();
    }

    private ParaderoConductorResponse mapearParadero(RutaParadero rutaParadero) {
        Paradero paradero = rutaParadero.getParadero();
        return ParaderoConductorResponse.builder()
                .rutaParaderoID(rutaParadero.getId())
                .paraderoID(paradero.getId())
                .orden(rutaParadero.getOrden())
                .nombre(paradero.getNombre())
                .direccion(paradero.getDireccion())
                .referencia(paradero.getReferencia())
                .latitud(paradero.getLatitud())
                .longitud(paradero.getLongitud())
                .estado(rutaParadero.getEstado() != null ? rutaParadero.getEstado() : paradero.getEstado())
                .build();
    }

    private RecorridoConductorResponse mapearRecorrido(Recorrido recorrido) {
        return RecorridoConductorResponse.builder()
                .id(recorrido.getId())
                .asignacionID(recorrido.getAsignacion().getId())
                .fecha(recorrido.getAsignacion().getFecha())
                .horaInicio(recorrido.getHoraInicio())
                .horaFin(recorrido.getHoraFin())
                .estado(recorrido.getEstado())
                .build();
    }
}
