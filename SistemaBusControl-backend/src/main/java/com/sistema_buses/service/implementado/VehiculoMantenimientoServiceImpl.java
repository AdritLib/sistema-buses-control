package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoRequest;
import com.sistema_buses.dto.vehiculo.VehiculoMantenimientoResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.model.Vehiculo;
import com.sistema_buses.model.VehiculoMantenimiento;
import com.sistema_buses.repository.VehiculoMantenimientoRepository;
import com.sistema_buses.repository.VehiculoRepository;
import com.sistema_buses.service.VehiculoMantenimientoService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoMantenimientoServiceImpl implements VehiculoMantenimientoService{
	private final VehiculoMantenimientoRepository repository;
	private final VehiculoRepository vehiculoRepository;
	private final ApplicationConfig config;
	private final RabbitProducer producer;
	private final String entidadAfectada = "Vehiculo Mantenimiento";
	
	@Override
	public List<VehiculoMantenimientoResponse> listar(int pagina) {
		return repository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}

	@Override
	public VehiculoMantenimientoResponse encontrarPorID(Long vehiculoMantenimientoID) {
		return repository.encontrarPorID(vehiculoMantenimientoID).orElseThrow();
	}

	@Override
	public VehiculoMantenimientoResponse registrar(VehiculoMantenimientoRequest request) {
		return guardar(null, request);
	}

	@Override
	public VehiculoMantenimientoResponse actualizar(Long vehiculoMantenimientoID, VehiculoMantenimientoRequest request) {
		return guardar(vehiculoMantenimientoID, request);
	}

	@Transactional
	private VehiculoMantenimientoResponse guardar(Long vehiculoMantenimientoID, VehiculoMantenimientoRequest request) {
		VehiculoMantenimiento entidad;
		RegistroAccion accion;
		if(vehiculoMantenimientoID == null) {
			entidad = new VehiculoMantenimiento();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = repository.findById(vehiculoMantenimientoID).orElseThrow();
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setFechaInicio(request.getFechaInicio());
		entidad.setFechaFin(request.getFechaFin());
		entidad.setEstado(request.getEstado());
		entidad.setDescripcion(request.getDescripcion());
		
		Vehiculo vehiculo = vehiculoRepository.findById(request.getVehiculoID()).orElseThrow();
		entidad.setVehiculo(vehiculo);
		
		VehiculoMantenimiento guardado = repository.save(entidad);
		producer.enviar(accion, guardado.toString(), entidadAfectada);
		return VehiculoMantenimientoResponse.builder()
				.fechaInicio(guardado.getFechaInicio())
				.fechaFin(guardado.getFechaFin())
				.descripcion(guardado.getDescripcion())
				.vehiculoID(guardado.getVehiculo().getId())
				.estado(guardado.getEstado())
				.id(guardado.getId())
				.vehiculoPlaca(vehiculo.getPlaca())
				.build();
	}

	@Override
	public void eliminar(Long vehiculoMantenimientoID) {
		repository.deleteById(vehiculoMantenimientoID);
	}
}
