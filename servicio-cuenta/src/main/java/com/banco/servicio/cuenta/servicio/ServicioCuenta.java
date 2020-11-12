package com.banco.servicio.cuenta.servicio;

import java.util.List;

import com.banco.servicio.cuenta.entidad.Cuenta;

public interface ServicioCuenta {

	List<Cuenta> obtenerCuentas(int idCliente);

	boolean registrarCuentas(List<Cuenta> cuentas);

	boolean actualizarCuentas(List<Cuenta> cuentas);

	boolean eliminarCuentas(int idCliente);

}
