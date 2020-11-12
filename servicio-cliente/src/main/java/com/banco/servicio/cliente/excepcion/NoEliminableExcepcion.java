package com.banco.servicio.cliente.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class NoEliminableExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public NoEliminableExcepcion() {
		super();
	}

	public NoEliminableExcepcion(String mensaje) {
		super(mensaje);
	}

	public NoEliminableExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
