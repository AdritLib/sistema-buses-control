package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.dto.EstacionRequest;
import com.sistema_buses.dto.EstacionResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.exception.EstacionNoEncontradoException;
import com.sistema_buses.mapper.EstacionMapper;
import com.sistema_buses.model.Estacion;
import com.sistema_buses.repository.EstacionRepository;
import com.sistema_buses.service.EstacionService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstacionServiceImpl implements EstacionService {
	private final EstacionRepository estacionRepository;
	private final RabbitProducer producer;
	private final String nombreEntidad = "Estacion";
	private final EstacionMapper estacionMapper;
	
	@Override
	public List<EstacionResponse> listar(int pagina, int size) {
		return estacionRepository.listar(PageRequest.of(pagina, size)).toList();
	}

	@Override
	public EstacionResponse encontrarPorID(Long estacionID) {
		return estacionRepository.encontrarPorID(estacionID).orElseThrow(() -> new EstacionNoEncontradoException(estacionID));
	}
	
	@Override
	@Transactional
	public EstacionResponse registrar(EstacionRequest request) {
		return guardar(null, request);
	}

	@Override
	@Transactional
	public EstacionResponse actualizar(Long estacionID, EstacionRequest request) {
		return guardar(estacionID, request);
	}
	
	@Transactional
	private EstacionResponse guardar(Long id, EstacionRequest request) {
		Estacion entidad = estacionMapper.toEntity(request);
		RegistroAccion accion = RegistroAccion.INSERTAR;
		if(id != null) {
			if(!estacionRepository.existsById(id)) throw new EstacionNoEncontradoException(id);
			entidad.setId(id);
			accion = RegistroAccion.ACTUALIZAR;
		}
		
		Estacion guardado = estacionRepository.save(entidad);
		producer.enviar(accion, guardado.toString(), nombreEntidad);
		
		return estacionMapper.toResponse(guardado);
	}
}
