package com.sistema_buses.service.implementado;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.incidencia.IncidenciaConductorRequest;
import com.sistema_buses.dto.incidencia.IncidenciaRequest;
import com.sistema_buses.dto.incidencia.IncidenciaResponse;
import com.sistema_buses.exception.ErrorDeNegocioException;
import com.sistema_buses.enums.RecorridoEstado;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.model.Incidencia;
import com.sistema_buses.model.Recorrido;
import com.sistema_buses.model.Usuario;
import com.sistema_buses.repository.IncidenciaRepository;
import com.sistema_buses.repository.RecorridoRepository;
import com.sistema_buses.repository.UsuarioRepository;
import com.sistema_buses.service.IncidenciaService;
import com.sistema_buses.service.autenticacion.UsuarioAutenticadoService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidenciaServiceImpl implements IncidenciaService {
	private final IncidenciaRepository incidenciaRepository;
	private final UsuarioRepository usuarioRepository;
	private final RecorridoRepository recorridoRepository;
	private final UsuarioAutenticadoService usuarioAutenticadoService;
	private final RabbitProducer producer;
	private final ApplicationConfig config;
	private final String entidadAfectada = "Incidencia";
	
	@Override
	public List<IncidenciaResponse> listar(int pagina) {
		return incidenciaRepository
				.findAllByOrderByFechaHoraSucesoDesc(PageRequest.of(pagina, config.registrosPorPagina()))
				.stream()
				.map(this::mapear)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<IncidenciaResponse> listarParaConductor(Long usuarioID, int pagina, int size) {
		usuarioAutenticadoService.validarMismoUsuarioOAdmin(usuarioID);
		int paginaSegura = Math.max(pagina, 0);
		int sizeSeguro = Math.min(Math.max(size, 1), 50);
		return incidenciaRepository
				.findByUsuarioIdOrderByFechaHoraSucesoDesc(
						usuarioID,
						PageRequest.of(paginaSegura, sizeSeguro))
				.stream()
				.map(this::mapear)
				.toList();
	}

	@Override
	public IncidenciaResponse encontrarPorID(Long incidencia) {
		return incidenciaRepository.findById(incidencia).map(this::mapear).orElseThrow();
	}

	@Override
	@Transactional
	public IncidenciaResponse registrar(IncidenciaRequest request) {
		return guardar(null, request, RegistroAccion.INSERTAR);
	}

	@Override
	@Transactional
	public IncidenciaResponse registrarComoConductor(IncidenciaConductorRequest request) {
		Long usuarioID = usuarioAutenticadoService.obtenerUsuarioID();
		Recorrido recorrido = recorridoRepository.findById(request.getRecorridoID())
				.orElseThrow(() -> new ErrorDeNegocioException("No hay recorrido activo para registrar la incidencia."));
		validarRecorridoActivo(recorrido);
		if (!usuarioAutenticadoService.esAdmin()
				&& (recorrido.getAsignacion().getConductor() == null
						|| !usuarioID.equals(recorrido.getAsignacion().getConductor().getId()))) {
			throw new AccessDeniedException("No puedes reportar incidencias en un recorrido ajeno.");
		}

		IncidenciaRequest requestInterno = IncidenciaRequest.builder()
				.recorridoID(request.getRecorridoID())
				.usuarioID(usuarioID)
				.descripcion(request.getDescripcion())
				.fechaHoraSuceso(LocalDateTime.now())
				.build();
		return guardar(null, requestInterno, RegistroAccion.INCIDENCIA_REGISTRADA);
	}

	@Override
	@Transactional
	public IncidenciaResponse actualizar(Long incidencia, IncidenciaRequest request) {
		return guardar(incidencia, request, RegistroAccion.ACTUALIZAR);
	}
	
	private IncidenciaResponse guardar(Long incidenciaID, IncidenciaRequest request, RegistroAccion accion) {
		Incidencia entidad;
		if(incidenciaID == null) {
			entidad = new Incidencia();
		}else {
			entidad = incidenciaRepository.findById(incidenciaID).orElseThrow();
		}
		entidad.setFechaHoraSuceso(request.getFechaHoraSuceso() != null
				? request.getFechaHoraSuceso() : LocalDateTime.now());
		entidad.setDescripcion(request.getDescripcion());
		
		Usuario usuario = usuarioRepository.findById(request.getUsuarioID()).orElseThrow();
		entidad.setUsuario(usuario);

		Recorrido recorrido = recorridoRepository.findById(request.getRecorridoID())
				.orElseThrow(() -> new ErrorDeNegocioException("No hay recorrido activo para registrar la incidencia."));
		if (incidenciaID == null) {
			validarRecorridoActivo(recorrido);
		}
		entidad.setRecorrido(recorrido);
		
		Incidencia guardado = incidenciaRepository.save(entidad);
		String descripcionEvento = accion == RegistroAccion.INCIDENCIA_REGISTRADA
				? "recorrido=" + guardado.getRecorrido().getId()
				: "Se " + accion.verbo() + " una incidencia";
		producer.enviar(accion, descripcionEvento, entidadAfectada);
		
		return mapear(guardado);
	}

	private IncidenciaResponse mapear(Incidencia incidencia) {
		Recorrido recorrido = incidencia.getRecorrido();
		return IncidenciaResponse.builder()
				.usuarioID(incidencia.getUsuario().getId())
				.recorridoID(recorrido.getId())
				.id(incidencia.getId())
				.fechaHoraSuceso(incidencia.getFechaHoraSuceso())
				.descripcion(incidencia.getDescripcion())
				.usuarioNombre(nombreConductor(incidencia))
				
				.ruta(recorrido.getAsignacion() != null && recorrido.getAsignacion().getRuta() != null
						? recorrido.getAsignacion().getRuta().getNombre()
						: null)
				.placa(recorrido.getAsignacion() != null && recorrido.getAsignacion().getVehiculo() != null
						? recorrido.getAsignacion().getVehiculo().getPlaca()
						: null)
				.vehiculo(recorrido.getAsignacion() != null && recorrido.getAsignacion().getVehiculo() != null
						? recorrido.getAsignacion().getVehiculo().getMarca() + " "
								+ recorrido.getAsignacion().getVehiculo().getModelo()
						: null)
				.estadoRecorrido(recorrido.getEstado() != null ? recorrido.getEstado().name() : null)
				.build();
	}

	private void validarRecorridoActivo(Recorrido recorrido) {
		if (recorrido.getEstado() != RecorridoEstado.EN_CURSO) {
			throw new ErrorDeNegocioException("No hay recorrido activo para registrar la incidencia.");
		}
	}

	private String nombreConductor(Incidencia incidencia) {
		if (incidencia.getUsuario() != null && incidencia.getUsuario().getNombre() != null) {
			return incidencia.getUsuario().getNombre();
		}
		Recorrido recorrido = incidencia.getRecorrido();
		if (recorrido.getAsignacion() != null && recorrido.getAsignacion().getConductor() != null) {
			return recorrido.getAsignacion().getConductor().getNombre();
		}
		return null;
	}

	@Override
	public void eliminar(Long id) {
		Incidencia entidad = incidenciaRepository.findById(id).orElseThrow();
		incidenciaRepository.delete(entidad);

		producer.enviar(RegistroAccion.ELIMINAR, entidadAfectada);
	}
}

