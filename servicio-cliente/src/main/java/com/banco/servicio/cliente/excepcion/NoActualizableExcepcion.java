package com.banco.servicio.cliente.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class NoActualizableExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public NoActualizableExcepcion() {
		super();
	}

	public NoActualizableExcepcion(String mensaje) {
		super(mensaje);
	}

	public NoActualizableExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
