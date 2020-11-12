package com.banco.servicio.cuenta.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class NoRegistrableExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRegistrableExcepcion() {
		super();
	}

	public NoRegistrableExcepcion(String mensaje) {
		super(mensaje);
	}

	public NoRegistrableExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
