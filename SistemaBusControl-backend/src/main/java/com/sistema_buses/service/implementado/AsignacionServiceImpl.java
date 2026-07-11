package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.asignacion.AsignacionRequest;
import com.sistema_buses.dto.asignacion.AsignacionResponse;
import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.exception.ErrorDeNegocioException;
import com.sistema_buses.exception.UsuarioNoEncontradoException;
import com.sistema_buses.exception.VehiculoNoEncontradoException;
import com.sistema_buses.model.Asignacion;
import com.sistema_buses.model.Ruta;
import com.sistema_buses.model.Usuario;
import com.sistema_buses.model.Vehiculo;
import com.sistema_buses.repository.AsignacionRepository;
import com.sistema_buses.repository.RutaRepository;
import com.sistema_buses.repository.UsuarioRepository;
import com.sistema_buses.repository.VehiculoRepository;
import com.sistema_buses.service.AsignacionService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionServiceImpl implements AsignacionService {
	private final AsignacionRepository asignacionRepository;
	private final UsuarioRepository usuarioRepository;
	private final RutaRepository rutaRepository;
	private final VehiculoRepository vehiculoRepository;
	private final RabbitProducer producer;
	private final ApplicationConfig config;
	private final String nombreEntidad = "Asignacion";
	
	@Override
	public List<AsignacionResponse> listar(int pagina) {
		return asignacionRepository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}
	
	@Override
	public AsignacionResponse obtenerPorID(Long asignacionID) {
		return asignacionRepository.encontrarPorID(asignacionID).orElseThrow();
	}

	@Override
	public AsignacionResponse registrar(AsignacionRequest request) {
		return guardar(null, request);
	}

	@Override
	public AsignacionResponse actualizar(Long asignacionID, AsignacionRequest request) {
		return guardar(asignacionID, request);
	}
	
	@Override
	public void asignarConductor(Long asignacionID, Long usuarioID) {
		asignacionRepository.asignarConductor(asignacionID, usuarioID);
	}

	@Override
	public void asignarRuta(Long asignacionID, Long rutaID) {
		asignacionRepository.asignarRuta(asignacionID, rutaID);
	}
	
	@Transactional
	public AsignacionResponse guardar(Long asignacionID, AsignacionRequest request) {
		Asignacion entidad;
		RegistroAccion accion;
		if(asignacionID == null) {
			entidad = new Asignacion();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = asignacionRepository.findById(asignacionID).orElseThrow();
			accion = RegistroAccion.ACTUALIZAR;
		}
		
		Usuario conductor = usuarioRepository.findById(request.getConductorID()).orElseThrow(UsuarioNoEncontradoException::new);
		entidad.setConductor(conductor);

		Ruta ruta = rutaRepository.findById(request.getRutaID()).orElseThrow();
		entidad.setRuta(ruta);
		
		Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoID()).orElseThrow(VehiculoNoEncontradoException::new);
		entidad.setVehiculo(vehiculo);
		
		entidad.setFecha(request.getFecha());
		
		entidad.setHoraInicio(request.getHoraInicio());
		entidad.setHoraFin(request.getHoraFin());
		entidad.setEstado(GenericoEstado.ACTIVO);
		
		Asignacion guardado = asignacionRepository.save(entidad);
		producer.enviar(accion, nombreEntidad);
		return mapear(guardado);
	}

	@Override
	public void eliminarPorID(Long id) {
		Asignacion entidad = asignacionRepository.findById(id)
				.orElseThrow(() -> new ErrorDeNegocioException("Asignación no encontrada"));

		asignacionRepository.delete(entidad);

		producer.enviar(RegistroAccion.ELIMINAR, nombreEntidad);
	}
	
	public AsignacionResponse mapear(Asignacion guardado) {
		return AsignacionResponse.builder()
				.fecha(guardado.getFecha())
				.conductorID(guardado.getConductor().getId())
				.conductorNombre(guardado.getConductor().getNombre())
				.horaFin(guardado.getHoraFin())
				.horaInicio(guardado.getHoraInicio())
				.rutaID(guardado.getRuta().getId())
				.id(guardado.getId())
				.build();
	}
}
