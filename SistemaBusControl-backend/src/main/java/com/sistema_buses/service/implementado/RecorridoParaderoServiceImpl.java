package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.recorrido.RecorridoParaderoRequest;
import com.sistema_buses.dto.recorrido.RecorridoParaderoResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.model.Recorrido;
import com.sistema_buses.model.RecorridoParadero;
import com.sistema_buses.model.RutaParadero;
import com.sistema_buses.repository.RecorridoParaderoRepository;
import com.sistema_buses.repository.RecorridoRepository;
import com.sistema_buses.repository.RutaParaderoRepository;
import com.sistema_buses.service.RecorridoParaderoService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecorridoParaderoServiceImpl implements RecorridoParaderoService {
	private final RecorridoParaderoRepository recorridoParaderoRepository;
	private final RecorridoRepository recorridoRepository;
	private final RutaParaderoRepository rutaParaderoRepository;
	private final RabbitProducer producer;
	private final ApplicationConfig config;
	private final String entidadAfectada = "Recorrido Paradero";
	
	@Override
	public List<RecorridoParaderoResponse> listar(int pagina) {
		return recorridoParaderoRepository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}

	@Override
	public RecorridoParaderoResponse encontrarPorID(Long recorridoParaderoID) {
		return recorridoParaderoRepository.encontrarPorID(recorridoParaderoID).orElseThrow();
	}

	@Override
	public RecorridoParaderoResponse registrar(RecorridoParaderoRequest request) {
		return guardar(null, request);
	}

	@Override
	public RecorridoParaderoResponse actualizar(Long recorridoParaderoID, RecorridoParaderoRequest request) {
		return guardar(recorridoParaderoID, request);
	}

	@Transactional
	private RecorridoParaderoResponse guardar(Long recorridoParaderoID, RecorridoParaderoRequest request) {
		RecorridoParadero entidad;
		RegistroAccion accion;
		if(recorridoParaderoID == null) {
			entidad = new RecorridoParadero();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = recorridoParaderoRepository.findById(recorridoParaderoID).orElseThrow();
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setEstadoLlegada(request.getEstado());
		entidad.setObservaciones(request.getObservaciones());
		
		Recorrido recorrido = recorridoRepository.findById(request.getRecorridoID()).orElseThrow();
		entidad.setRecorrido(recorrido);
		
		RutaParadero rutaParadero = rutaParaderoRepository.findById(request.getRutaParaderoID()).orElseThrow();
		entidad.setRutaParadero(rutaParadero);
		
		RecorridoParadero guardado = recorridoParaderoRepository.save(entidad);
		producer.enviar(accion, entidadAfectada);
		return RecorridoParaderoResponse.builder()
				.estado(guardado.getEstadoLlegada())
				.id(guardado.getId())
				.recorridoID(guardado.getRecorrido().getId())
				.rutaParaderoID(guardado.getRutaParadero().getId())
				.observaciones(guardado.getObservaciones())
				.build();
	}

}
