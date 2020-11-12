package com.banco.servicio.cuenta.controlador.impl;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.banco.servicio.cuenta.controlador.ControladorCuenta;
import com.banco.servicio.cuenta.dto.Respuesta;
import com.banco.servicio.cuenta.entidad.Cuenta;
import com.banco.servicio.cuenta.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cuenta.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cuenta.excepcion.NoEliminableExcepcion;
import com.banco.servicio.cuenta.excepcion.NoRegistrableExcepcion;
import com.banco.servicio.cuenta.servicio.ServicioCuenta;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ControladorCuentaImpl implements ControladorCuenta {

	private final ServicioCuenta servicioCuenta;

	@Override
	public ResponseEntity<List<Cuenta>> obtenerCuentas(int idCliente) throws ErrorInternoExcepcion {
		try {
			return ok(this.servicioCuenta.obtenerCuentas(idCliente));
		} catch (Exception excepcion) {
			throw new ErrorInternoExcepcion(
					"Ocurrio un error al consultar las cuentas del cliente con identificador " + idCliente, excepcion);
		}
	}

	@Override
	public ResponseEntity<Respuesta> registrarCuentas(List<Cuenta> cuentas) throws NoRegistrableExcepcion {
		try {
			return ok(new Respuesta("Se registraron correctamente las cuentas",
					this.servicioCuenta.registrarCuentas(cuentas)));
		} catch (Exception excepcion) {
			throw new NoRegistrableExcepcion("Ocurrio un error al registrar las cuentas.", excepcion);
		}
	}

	@Override
	public ResponseEntity<Respuesta> actualizarCuentas(List<Cuenta> cuentas) throws NoActualizableExcepcion {
		try {
			return ok(new Respuesta("Se actualizaron correctamente las cuentas",
					this.servicioCuenta.actualizarCuentas(cuentas)));
		} catch (Exception excepcion) {
			throw new NoActualizableExcepcion("Ocurrio un error al actualizar las cuentas.", excepcion);
		}
	}

	@Override
	public ResponseEntity<Respuesta> eliminarCuentas(int idCliente) throws NoEliminableExcepcion {
		try {
			return ok(new Respuesta("Se eliminaron correctamente las cuentas",
					this.servicioCuenta.eliminarCuentas(idCliente)));
		} catch (Exception excepcion) {
			throw new NoEliminableExcepcion(
					"Ocurrio un error al intentar eliminar las cuentas del cliente con identificador " + idCliente,
					excepcion);
		}
	}

}
