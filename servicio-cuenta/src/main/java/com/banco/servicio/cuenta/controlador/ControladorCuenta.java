package com.banco.servicio.cuenta.controlador;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banco.servicio.cuenta.dto.Falla;
import com.banco.servicio.cuenta.dto.Respuesta;
import com.banco.servicio.cuenta.entidad.Cuenta;
import com.banco.servicio.cuenta.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cuenta.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cuenta.excepcion.NoEliminableExcepcion;
import com.banco.servicio.cuenta.excepcion.NoRegistrableExcepcion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Servicios para gestionar las cuentas bancarias asociadas a un cliente.")
@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Success|OK"),
		@ApiResponse(code = 401, message = "Bad Request", response = Falla.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = Falla.class) })
@RequestMapping(path = "/cuentas")
@Validated
public interface ControladorCuenta {

	@ApiOperation(value = "Obtiene todas las cuentas bancarias asociadas a un cliente.", response = Cuenta.class, responseContainer = "List")
	@GetMapping(path = "/{idCliente}", produces = APPLICATION_JSON_VALUE)
	@Validated
	ResponseEntity<List<Cuenta>> obtenerCuentas(
			@PathVariable("idCliente") @Positive(message = "El identificador del cliente no puede ser negativo o cero.") int idCliente)
			throws ErrorInternoExcepcion;

	@ApiOperation(value = "Registra como minimo 1 o multiples cuentas bancarias que se asociaran a un cliente.", response = Respuesta.class)
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@Validated(Cuenta.EventoRegistro.class)
	ResponseEntity<Respuesta> registrarCuentas(
			@RequestBody @Size(min = 1, message = "Debe al menos registrarse {min} cuenta asociada.", groups = Cuenta.EventoRegistro.class) List<@Valid Cuenta> cuentas)
			throws NoRegistrableExcepcion;

	@ApiOperation(value = "Actualiza todas las cuentas bancarias asociadas a un cliente.", response = Respuesta.class)
	@PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@Validated(Cuenta.EventoActualizacion.class)
	ResponseEntity<Respuesta> actualizarCuentas(
			@RequestBody @Size(min = 1, message = "Debe al menos actualizarce {min} cuenta asociada.", groups = Cuenta.EventoRegistro.class) List<@Valid Cuenta> cuentas)
			throws NoActualizableExcepcion;

	@ApiOperation(value = "Elimina todas las cuentas bancarias asociadas a un cliente.", response = Respuesta.class)
	@DeleteMapping(path = "/{idCliente}", produces = APPLICATION_JSON_VALUE)
	@Validated
	ResponseEntity<Respuesta> eliminarCuentas(
			@PathVariable("idCliente") @Positive(message = "El identificador del cliente no puede ser negativo o cero.") int idCliente)
			throws NoEliminableExcepcion;
}
