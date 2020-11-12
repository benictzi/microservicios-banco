package com.banco.servicio.cuenta.servicio.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banco.servicio.cuenta.entidad.Cuenta;
import com.banco.servicio.cuenta.repositorio.RepositorioCuenta;
import com.banco.servicio.cuenta.servicio.ServicioCuenta;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioCuentaImpl implements ServicioCuenta {

	private final RepositorioCuenta repositorioCuenta;

	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> obtenerCuentas(int idCliente) {
		return this.repositorioCuenta.findAllByIdCliente(idCliente);
	}

	@Override
	@Transactional
	public boolean registrarCuentas(List<Cuenta> cuentas) {
		List<Cuenta> cuentasRegistradas = this.repositorioCuenta.saveAll(cuentas);
		return nonNull(cuentasRegistradas) && (!cuentasRegistradas.isEmpty())
				&& cuentasRegistradas.size() == cuentas.size() 
				&& cuentasRegistradas.stream().allMatch(cuenta -> {
					return cuenta.getIdCuenta() > 0;
				});
	}

	@Override
	@Transactional
	public boolean actualizarCuentas(List<Cuenta> cuentas) {
		List<Cuenta> cuentasActualizadas = this.repositorioCuenta.saveAll(cuentas);
		return nonNull(cuentasActualizadas) && (!cuentasActualizadas.isEmpty())
				&& cuentasActualizadas.size() == cuentas.size();
	}

	@Override
	@Transactional
	public boolean eliminarCuentas(int idCliente) {
		List<Cuenta> cuentas = this.repositorioCuenta.findAllByIdCliente(idCliente);
		this.repositorioCuenta.deleteAllByIdCliente(idCliente);
		List<Integer> idsCuentas = cuentas.stream().map(Cuenta::getIdCuenta).collect(toList());
		List<Cuenta> cuentasResultantes = this.repositorioCuenta.findAllById(idsCuentas);
		return isNull(cuentasResultantes) || cuentasResultantes.isEmpty();
	}

}
