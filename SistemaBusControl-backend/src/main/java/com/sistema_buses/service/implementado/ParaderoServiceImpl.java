package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.dto.paradero.ParaderoRequest;
import com.sistema_buses.dto.paradero.ParaderoResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.exception.ParaderoNoEncontradoException;
import com.sistema_buses.model.Paradero;
import com.sistema_buses.repository.ParaderoRepository;
import com.sistema_buses.service.ParaderoService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParaderoServiceImpl implements ParaderoService {
	private final ParaderoRepository paraderoRepository;
	private final RabbitProducer producer;
	private final String nombreEntidad = "Paradero";
	
	@Override
	public List<ParaderoResponse> listar(int pagina, int size) {
		return paraderoRepository.listar(PageRequest.of(pagina, size)).toList();
	}

	@Override
	public ParaderoResponse encontrarPorID(Long paraderoID) {
		return paraderoRepository.encontrarPorID(paraderoID).orElseThrow(() -> new ParaderoNoEncontradoException(paraderoID));
	}
	
	@Override
	@Transactional
	public ParaderoResponse registrar(ParaderoRequest request) {
		return guardar(null, request);
	}

	@Override
	public ParaderoResponse actualizar(Long paraderoID, ParaderoRequest request) {
		return guardar(paraderoID, request);
	}

	@Override
	@Transactional
	public void eliminarPorID(Long paraderoID) {
		paraderoRepository.deleteById(paraderoID);
		producer.enviar(RegistroAccion.ELIMINAR, nombreEntidad);
	}

	@Transactional
	private ParaderoResponse guardar(Long paraderoID, ParaderoRequest request) {
		Paradero entidad;
		RegistroAccion accion;
		if(paraderoID == null) {
			entidad = new Paradero();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = paraderoRepository.findById(paraderoID).orElseThrow(() -> new ParaderoNoEncontradoException(paraderoID));
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setLatitud(request.getLatitud());
		entidad.setLongitud(request.getLongitud());
		entidad.setNombre(request.getNombre());
		entidad.setReferencia(request.getReferencia());
		entidad.setDireccion(request.getDireccion());
		entidad.setEstado(request.getEstado());
		
		Paradero guardado = paraderoRepository.save(entidad);
		producer.enviar(accion, nombreEntidad);
		
		return ParaderoResponse.builder()
				.id(guardado.getId())
				.latitud(guardado.getLatitud())
				.longitud(guardado.getLongitud())
				.nombre(guardado.getNombre())
				.referencia(guardado.getReferencia())
				.direccion(guardado.getDireccion())
				.estado(guardado.getEstado())
				.build();
	}
}
