package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.dto.ruta.RutaParaderoRequest;
import com.sistema_buses.dto.ruta.RutaParaderoResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.model.Paradero;
import com.sistema_buses.model.Ruta;
import com.sistema_buses.model.RutaParadero;
import com.sistema_buses.repository.ParaderoRepository;
import com.sistema_buses.repository.RutaParaderoRepository;
import com.sistema_buses.repository.RutaRepository;
import com.sistema_buses.service.RutaParaderoService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutaParaderoServiceImpl implements RutaParaderoService {
	private final ApplicationConfig config;
	private final RutaParaderoRepository rutaParaderoRepository;
	private final RutaRepository rutaRepository;
	private final ParaderoRepository paraderoRepository;
	private final RabbitProducer producer;
	private final String entidadAfectada = "Ruta Paradero";
	
	@Override
	public List<RutaParaderoResponse> listar(int pagina) {
		return rutaParaderoRepository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}

	@Override
	public RutaParaderoResponse encontrarPorID(Long estacionID) {
		return rutaParaderoRepository.encontrarPorID(estacionID).orElseThrow();
	}

	@Override
	public RutaParaderoResponse registrar(RutaParaderoRequest request) {
		return guardar(null, request);
	}

	@Override
	public RutaParaderoResponse actualizar(Long rutaParaderoID, RutaParaderoRequest request) {
		return guardar(rutaParaderoID, request);
	}

	@Transactional
	private RutaParaderoResponse guardar(Long rutaParaderoID, RutaParaderoRequest request) {
		RutaParadero entidad;
		RegistroAccion accion;
		if(rutaParaderoID == null) {
			entidad = new RutaParadero();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = rutaParaderoRepository.findById(rutaParaderoID).orElseThrow();
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setOrden(request.getOrden());
		entidad.setEstado(request.getEstado());
		
		Ruta ruta = rutaRepository.findById(request.getRutaID()).orElseThrow();
		entidad.setRuta(ruta);
		
		Paradero paradero = paraderoRepository.findById(request.getParaderoID()).orElseThrow();
		entidad.setParadero(paradero);
		
		RutaParadero guardado = rutaParaderoRepository.save(entidad);
		producer.enviar(accion, entidadAfectada);
		
		return RutaParaderoResponse.builder()
				.estado(guardado.getEstado())
				.orden(guardado.getOrden())
				.rutaID(guardado.getRuta().getId())
				.paraderoID(guardado.getParadero().getId())
				.id(guardado.getId())
				.build();
	}

	@Override
	public List<ParaderoResponse> listarPorRuta(Long idRuta) {
		return paraderoRepository.listarPorRuta(idRuta);
	}
}
