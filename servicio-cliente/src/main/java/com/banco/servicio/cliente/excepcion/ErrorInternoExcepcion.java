package com.banco.servicio.cliente.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorInternoExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorInternoExcepcion() {
		super();
	}

	public ErrorInternoExcepcion(String mensaje) {
		super(mensaje);
	}

	public ErrorInternoExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}