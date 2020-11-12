package com.banco.servicio.cliente.servicio.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.servicio.cliente.dto.Cuenta;
import com.banco.servicio.cliente.dto.Respuesta;
import com.banco.servicio.cliente.entidad.Cliente;
import com.banco.servicio.cliente.http.ClienteHttpCuenta;
import com.banco.servicio.cliente.repositorio.RepositorioCliente;
import com.banco.servicio.cliente.servicio.ServicioCliente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioClienteImpl implements ServicioCliente {

	private final RepositorioCliente repositorioCliente;

	private final ClienteHttpCuenta clienteHttpCuenta;

	@Override
	@Transactional(readOnly = true)
	public Cliente obtenerCliente(int idCliente) {
		Optional<Cliente> posibleCliente = this.repositorioCliente.findById(idCliente);
		posibleCliente.ifPresent(cliente-> {
			cliente.setCuentas(this.clienteHttpCuenta.obtenerCuentas(idCliente));
		});
		return posibleCliente.orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> obtenerClientes() {
		List<Cliente> clientes = this.repositorioCliente.findAll();
		clientes.forEach(cliente -> {
			List<Cuenta> cuentas = this.clienteHttpCuenta.obtenerCuentas(cliente.getIdCliente());
			cliente.setCuentas(cuentas);
		});
		return clientes;
	}

	@Override
	@Transactional
	public boolean registrarCliente(Cliente cliente) {
		Cliente clienteCreado = this.repositorioCliente.save(cliente);
		cliente.getCuentas().forEach(cuenta -> {
			cuenta.setIdCliente(clienteCreado.getIdCliente());
		});
		Respuesta respuesta = this.clienteHttpCuenta.registrarCuentas(cliente.getCuentas());
		boolean cuentasRegistradasCorrectamente = respuesta.isOperacionCompletada();
		return Objects.nonNull(clienteCreado) && cuentasRegistradasCorrectamente;
	}

	@Override
	@Transactional
	public boolean actualizarCliente(Cliente cliente) {
		if (this.repositorioCliente.existsById(cliente.getIdCliente())) {
			Cliente clienteActualizado = this.repositorioCliente.save(cliente);
			cliente.getCuentas().forEach(cuenta -> {
				cuenta.setIdCliente(clienteActualizado.getIdCliente());
			});
			Respuesta respuesta = this.clienteHttpCuenta.actualizarCuentas(cliente.getCuentas());
			return Objects.nonNull(clienteActualizado) && respuesta.isOperacionCompletada();
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean eliminarCliente(int idCliente) {
		if (this.repositorioCliente.existsById(idCliente)) {
			this.repositorioCliente.deleteById(idCliente);
			this.clienteHttpCuenta.eliminarCuentas(idCliente);
			Respuesta respuesta = this.clienteHttpCuenta.eliminarCuentas(idCliente);
			return this.repositorioCliente.existsById(idCliente) && respuesta.isOperacionCompletada();
		} else {
			return false;
		}
	}

}
