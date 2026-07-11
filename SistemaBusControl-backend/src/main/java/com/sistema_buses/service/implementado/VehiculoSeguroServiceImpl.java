package com.sistema_buses.service.implementado;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_buses.config.ApplicationConfig;
import com.sistema_buses.dto.vehiculo.VehiculoSeguroRequest;
import com.sistema_buses.dto.vehiculo.VehiculoSeguroResponse;
import com.sistema_buses.enums.RegistroAccion;
import com.sistema_buses.model.VehiculoSeguro;
import com.sistema_buses.repository.VehiculoSeguroRepository;
import com.sistema_buses.service.VehiculoSeguroService;
import com.sistema_buses.service.rabbitmq.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehiculoSeguroServiceImpl implements VehiculoSeguroService{
	private final VehiculoSeguroRepository seguroRepository;
	private final ApplicationConfig config;
	private final RabbitProducer producer;
	private final String entidadAfectada = "Vehiculo Seguro";
	
	@Override
	public List<VehiculoSeguroResponse> listar(int pagina) {
		return seguroRepository.listar(PageRequest.of(pagina, config.registrosPorPagina())).toList();
	}

	@Override
	public VehiculoSeguroResponse encontrarPorID(Long vehiculoSeguroID) {
		return seguroRepository.encontrarPorID(vehiculoSeguroID).orElseThrow();
	}

	@Override
	public VehiculoSeguroResponse registrar(VehiculoSeguroRequest request) {
		return guardar(null, request);
	}

	@Override
	public VehiculoSeguroResponse actualizar(Long vehiculoSeguroID, VehiculoSeguroRequest request) {
		return guardar(vehiculoSeguroID, request);
	}

	@Transactional
	private VehiculoSeguroResponse guardar(Long vehiculoSeguroID, VehiculoSeguroRequest request) {
		VehiculoSeguro entidad;
		RegistroAccion accion;
		if(vehiculoSeguroID == null) {
			entidad = new VehiculoSeguro();
			accion = RegistroAccion.INSERTAR;
		}else {
			entidad = seguroRepository.findById(vehiculoSeguroID).orElseThrow();
			accion = RegistroAccion.ACTUALIZAR;
		}
		entidad.setNumero(request.getNumero());
		entidad.setFechaVencimiento(request.getFechaVencimiento());
		VehiculoSeguro guardado = seguroRepository.save(entidad);
		producer.enviar(accion, guardado.toString(), entidadAfectada);
		return VehiculoSeguroResponse.builder()
				.numero(guardado.getNumero())
				.fechaVencimiento(guardado.getFechaVencimiento())
				.build();
	}
	
	
}
