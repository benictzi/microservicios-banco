package com.banco.servicio.cliente.servicio;

import java.util.List;

import com.banco.servicio.cliente.entidad.Cliente;

public interface ServicioCliente {

	Cliente obtenerCliente(int idCliente);

	List<Cliente> obtenerClientes();

	boolean registrarCliente(Cliente cliente);

	boolean actualizarCliente(Cliente cliente);

	boolean eliminarCliente(int idCliente);
}
