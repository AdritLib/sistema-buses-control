package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaRequest;
import com.sistema_buses.dto.ruta.RutaResponse;
import com.sistema_buses.enums.GenericoEstado;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.exception.ErrorDeNegocioException;
import com.sistema_buses.exception.EstacionNoEncontradoException;
import com.sistema_buses.exception.RutaNoEncontradaException;
import com.sistema_buses.model.Estacion;
import com.sistema_buses.model.Ruta;
import com.sistema_buses.repository.EstacionRepository;
import com.sistema_buses.repository.RutaRepository;
import com.sistema_buses.service.RutaService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutaServiceImpl implements RutaService {
	private final RutaRepository rutaRepository;
	private final EstacionRepository estacionRepository;
	private final RabbitProducer producer;
	private final ApplicationConfig config;
	private final String nombreEntidad = "Ruta";
	
	@Override
	public List<RutaResponse> listar(int pagina) {
		return rutaRepository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}
	
	@Override
	public List<ParaderoResponse> listarParaderos(Long rutaID) {
		return rutaRepository.listarParaderos(rutaID);
	}

	@Override
	public RutaResponse encontrarPorID(Long rutaID) {
		return rutaRepository.encontrarPorID(rutaID).orElseThrow(() -> new RutaNoEncontradaException(rutaID));
	}
	
	@Override
	public RutaResponse registrar(RutaRequest request) {
		return guardar(null, request);
	}

	@Override
	public RutaResponse actualizar(Long rutaID, RutaRequest request) {
		return guardar(rutaID, request);
	}

	@Override
	@Transactional
	public void eliminarPorID(Long rutaID) {
		rutaRepository.deleteById(rutaID);
		producer.enviar(RegistroAccion.ELIMINAR, nombreEntidad);
	}

	@Override
	@Transactional
	public void agregarParadero(Long rutaID, Long paraderoID) {
		int resultado =  rutaRepository.agregarParadero(rutaID, paraderoID);
		
		if(resultado < 0) {
			throw new ErrorDeNegocioException("No se pudo agregar el paradero "+paraderoID+"  de la ruta "+rutaID);
		}
		producer.enviar(RegistroAccion.ACTUALIZAR, "Agregó el Paradero "+paraderoID+" en Ruta "+rutaID, nombreEntidad);
	}
	
	@Override
	@Transactional
	public void removerParadero(Long rutaID, Long paraderoID) {
		int resultado = rutaRepository.removerParadero(rutaID, paraderoID);
		
		if(resultado < 0) {
			throw new ErrorDeNegocioException("No se pudo remover el paradero "+paraderoID+"  de la ruta "+rutaID);
		}
		producer.enviar(RegistroAccion.ACTUALIZAR, "Removió el Paradero "+paraderoID+" de Ruta "+rutaID, nombreEntidad);
	}

	@Transactional
	private RutaResponse guardar(Long rutaID, RutaRequest request) {
		Ruta entidad;
		RegistroAccion accion;
		if(rutaID == null) {
			entidad = new Ruta();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = rutaRepository.findById(rutaID).orElseThrow(() -> new RutaNoEncontradaException(rutaID));
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setNombre(request.getNombre());
		entidad.setEstado(request.getEstado() == null ? GenericoEstado.INACTIVO : request.getEstado());
		entidad.setTipo(request.getTipo());
		
		Long estacionOrigenID = request.getEstacionOrigenId();
		Estacion estacionOrigen = estacionRepository.findById(estacionOrigenID).orElseThrow(() -> new EstacionNoEncontradoException(estacionOrigenID));
		entidad.setOrigen(estacionOrigen);
		
		Long estacionDestinoID = request.getEstacionDestinoId();
		Estacion estacionDestino = estacionRepository.findById(estacionDestinoID).orElseThrow(() -> new EstacionNoEncontradoException(estacionDestinoID));
		entidad.setDestino(estacionDestino);
		
		Ruta guardado = rutaRepository.save(entidad);
		producer.enviar(accion, nombreEntidad);
		
		return RutaResponse.builder()
				.id(guardado.getId())
				.nombre(guardado.getNombre())
				.estacionDestinoId(guardado.getDestino().getId())
				.estacionOrigenId(guardado.getOrigen().getId())
				.tipo(guardado.getTipo())
				.estado(guardado.getEstado())
				.build();
	}
}
