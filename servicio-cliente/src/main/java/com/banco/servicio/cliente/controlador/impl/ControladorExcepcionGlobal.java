package com.banco.servicio.cliente.controlador.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.banco.servicio.cliente.dto.Falla;
import com.banco.servicio.cliente.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cliente.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cliente.excepcion.NoEncontradoExcepcion;
import com.banco.servicio.cliente.excepcion.NoRegistrableExcepcion;

@RestControllerAdvice
public class ControladorExcepcionGlobal extends ResponseEntityExceptionHandler {

	public static List<String> obtenerMensajesErrores(Throwable excepcion) {
		List<String> lista = new ArrayList<>();
		while (excepcion != null) {
			lista.add(excepcion.getMessage());
			excepcion = excepcion.getCause();
		}
		return lista;
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> manejarErrorDeRegla(ConstraintViolationException excepcion) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Falla()
						.setCodigoEstadoHttp(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.setEstadoHttp(HttpStatus.INTERNAL_SERVER_ERROR.name())
						.setMensaje(excepcion.getMessage())
						.setErrores(obtenerMensajesErrores(excepcion)));
	}
	
	@ExceptionHandler(value = { NoEncontradoExcepcion.class })
	public ResponseEntity<Object> manejarNoEncontrado(NoEncontradoExcepcion excepcion) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Falla()
						.setCodigoEstadoHttp(HttpStatus.NOT_FOUND.value())
						.setEstadoHttp(HttpStatus.NOT_FOUND.name())
						.setMensaje(excepcion.getMessage())
						.setErrores(obtenerMensajesErrores(excepcion)));
	}
	
	@ExceptionHandler(value = { ErrorInternoExcepcion.class })
	public ResponseEntity<Object> manejarErrorInterno(ErrorInternoExcepcion excepcion) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Falla()
						.setCodigoEstadoHttp(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.setEstadoHttp(HttpStatus.INTERNAL_SERVER_ERROR.name())
						.setMensaje(excepcion.getMessage())
						.setErrores(obtenerMensajesErrores(excepcion)));
	}
	
	@ExceptionHandler(value = { NoActualizableExcepcion.class })
	public ResponseEntity<Object> manejarErrorNoActualizable(NoActualizableExcepcion excepcion) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(new Falla()
						.setCodigoEstadoHttp(HttpStatus.SERVICE_UNAVAILABLE.value())
						.setEstadoHttp(HttpStatus.SERVICE_UNAVAILABLE.name())
						.setMensaje(excepcion.getMessage())
						.setErrores(obtenerMensajesErrores(excepcion)));
	}
	
	@ExceptionHandler(value = { NoRegistrableExcepcion.class })
	public ResponseEntity<Object> manejarErrorNoRegistrable(NoRegistrableExcepcion excepcion) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(new Falla()
						.setCodigoEstadoHttp(HttpStatus.SERVICE_UNAVAILABLE.value())
						.setEstadoHttp(HttpStatus.SERVICE_UNAVAILABLE.name())
						.setMensaje(excepcion.getMessage())
						.setErrores(obtenerMensajesErrores(excepcion)));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
					.setCodigoEstadoHttp(status.value())
					.setEstadoHttp(status.name())
					.setMensaje("Argumentos no validos.")
					.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Tipo de medio no soportado(Solo se soporta json).")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Metodo no soportado, se deben usar correctamente los verbos HTTP al realizar una peticion.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("La peticion no logro procesarce correctamente, intentelo mas tarde.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Faltan parametros necesarios para realizar la solicitud.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("El recurso buscado es inexistente o se ha movido a otra direccion.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("El tipo de informacion vinculada al atributo no es valida, revise si los tipos de dato son asignables a los atributos.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Uno o varios parametros no se encuentran en la peticion.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Una o muchas variables de ruta no estan presentes.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Se produjo un error al realizar el binding de los atributos en el cuerpo de la peticion.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("Se produjo un error al realizar la conversion.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("La peticion no es legible.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje("El tipo de medio no es aceptable.")
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje(ex.getMessage())
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje(ex.getMessage())
						.setErrores(obtenerMensajesErrores(ex)));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status.value())
				.body(new Falla()
						.setCodigoEstadoHttp(status.value())
						.setEstadoHttp(status.name())
						.setMensaje(ex.getMessage())
						.setErrores(obtenerMensajesErrores(ex)));
	}
}
