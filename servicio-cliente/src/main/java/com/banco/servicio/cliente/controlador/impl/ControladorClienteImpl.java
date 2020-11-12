package com.banco.servicio.cliente.controlador.impl;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.banco.servicio.cliente.controlador.ControladorCliente;
import com.banco.servicio.cliente.dto.Respuesta;
import com.banco.servicio.cliente.entidad.Cliente;
import com.banco.servicio.cliente.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cliente.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cliente.excepcion.NoEliminableExcepcion;
import com.banco.servicio.cliente.excepcion.NoEncontradoExcepcion;
import com.banco.servicio.cliente.excepcion.NoRegistrableExcepcion;
import com.banco.servicio.cliente.servicio.ServicioCliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ControladorClienteImpl implements ControladorCliente {

	private final ServicioCliente servicioCliente;

	@Override
	public ResponseEntity<Cliente> obtenerCliente(int idCliente) throws ErrorInternoExcepcion, NoEncontradoExcepcion {
		try {
			Cliente cliente = this.servicioCliente.obtenerCliente(idCliente);
			if (Objects.isNull(cliente)) {
				throw new NoEncontradoExcepcion("No se encontro el cliente con identificador " + idCliente);
			}
			return ok(cliente);
		} catch (Exception excepcion) {
			throw new ErrorInternoExcepcion("Ocurrio un error al consultar el cliente con identificador " + idCliente,
					excepcion);
		}
	}

	@Override
	public ResponseEntity<List<Cliente>> obtenerClientes() throws ErrorInternoExcepcion {
		try {
			return ok(this.servicioCliente.obtenerClientes());
		} catch (Exception excepcion) {
			throw new ErrorInternoExcepcion("Ocurrio un error al consultar los clientes.", excepcion);
		}
	}

	@Override
	public ResponseEntity<Respuesta> registrarCliente(Cliente cliente) throws NoRegistrableExcepcion {
		try {
			return ok(new Respuesta("Se ha registrado correctamente el cliente.",
					this.servicioCliente.registrarCliente(cliente)));
		} catch (Exception excepcion) {
			throw new NoRegistrableExcepcion("Ocurrio un error al registrar el cliente.", excepcion);
		}
	}

	@Override
	public ResponseEntity<Respuesta> actualizarCliente(Cliente cliente) throws NoActualizableExcepcion {
		try {
			return ok(new Respuesta("Se ha actualizado correctamente el cliente.",
					this.servicioCliente.actualizarCliente(cliente)));
		} catch (Exception excepcion) {
			throw new NoActualizableExcepcion("Ocurrio un error al actualizar el cliente.", excepcion);
		}
	}

	@Override
	public ResponseEntity<Respuesta> eliminarCliente(int idCliente) throws NoEliminableExcepcion {
		try {
			return ok(new Respuesta("Se ha eliminado correctamente el cliente.",
					this.servicioCliente.eliminarCliente(idCliente)));
		} catch (Exception excepcion) {
			throw new NoEliminableExcepcion("Ocurrio un error al intentar eliminar el cliente " + idCliente, excepcion);
		}
	}

}
