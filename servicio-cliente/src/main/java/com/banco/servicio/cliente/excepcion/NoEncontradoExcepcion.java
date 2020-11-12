package com.banco.servicio.cliente.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoEncontradoExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public NoEncontradoExcepcion() {
		super();
	}

	public NoEncontradoExcepcion(String mensaje) {
		super(mensaje);
	}

	public NoEncontradoExcepcion(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
