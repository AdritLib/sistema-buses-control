package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.vehiculo.VehiculoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.exception.VehiculoNoEncontradoException;
import com.sistema_buses.model.Vehiculo;
import com.sistema_buses.repository.VehiculoRepository;
import com.sistema_buses.service.VehiculoService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {
	private final VehiculoRepository vehiculoRepository;
	private final RabbitProducer producer;
	private final ApplicationConfig config;
	private final String nombreEntidad = "Vehiculo";
	
	@Override
	public List<VehiculoResponse> listar(int pagina) {
		return vehiculoRepository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}

	@Override
	public VehiculoResponse encontrarPorID(Long vehiculoID) {
		return vehiculoRepository.encontrarPorID(vehiculoID).orElseThrow(() -> new VehiculoNoEncontradoException(vehiculoID));
	}
	
	@Override
	public VehiculoResponse registrar(VehiculoRequest request) {
		return guardar(null, request);
	}

	@Override
	public VehiculoResponse actualizar(Long vehiculoID, VehiculoRequest request) {
		return guardar(vehiculoID, request);
	}

	@Override
	@Transactional
	public void eliminarPorID(Long vehiculoID) {
		vehiculoRepository.deleteById(vehiculoID);
		producer.enviar(RegistroAccion.ELIMINAR, nombreEntidad);
	}

	@Transactional
	private VehiculoResponse guardar(Long vehiculoID, VehiculoRequest request) {
		Vehiculo entidad;
		RegistroAccion accion;
		if(vehiculoID == null) {
			entidad = new Vehiculo();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = vehiculoRepository.findById(vehiculoID).orElseThrow();
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setPlaca(request.getPlaca());
		entidad.setModelo(request.getModelo());
		entidad.setMarca(request.getMarca());
		entidad.setEstado(request.getEstado());
		entidad.setNumAsientos(request.getNumAsientos());
		entidad.setYear(request.getYear());
		
		Vehiculo guardado = vehiculoRepository.save(entidad);
		producer.enviar(accion, nombreEntidad);
		
		return VehiculoResponse.builder()
				.id(guardado.getId())
				.placa(guardado.getPlaca())
				.estado(guardado.getEstado())
				.marca(guardado.getMarca())
				.modelo(guardado.getModelo())
				.year(guardado.getYear())
				.numAsientos(guardado.getNumAsientos())
				.build();
	}
}
