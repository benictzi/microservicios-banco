package com.banco.servicio.cliente.controlador;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banco.servicio.cliente.dto.Falla;
import com.banco.servicio.cliente.dto.Respuesta;
import com.banco.servicio.cliente.entidad.Cliente;
import com.banco.servicio.cliente.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cliente.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cliente.excepcion.NoEliminableExcepcion;
import com.banco.servicio.cliente.excepcion.NoEncontradoExcepcion;
import com.banco.servicio.cliente.excepcion.NoRegistrableExcepcion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Servicios para gestionar a los clientes.")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
		@ApiResponse(code = 401, message = "Bad Request", response = Falla.class),
		@ApiResponse(code = 404, message = "Not Found", response = Falla.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = Falla.class) })
@RequestMapping(path = "/clientes")
@Validated
public interface ControladorCliente {

	@ApiOperation(value = "Obtiene un cliente en especifico.", response = Cliente.class)
	@GetMapping(path = "/{idCliente}", produces = APPLICATION_JSON_VALUE)
	@Validated
	ResponseEntity<Cliente> obtenerCliente(
			@PathVariable("idCliente") @Positive(message = "El identificador del cliente no puede ser negativo o cero.") int idCliente)
			throws ErrorInternoExcepcion, NoEncontradoExcepcion;

	@ApiOperation(value = "Obtiene una lista de todos los clientes del banco.", response = Cliente.class, responseContainer = "List")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	ResponseEntity<List<Cliente>> obtenerClientes() throws ErrorInternoExcepcion;

	@ApiOperation(value = "Registra a un cliente con una o varias cuentas bancarias.", response = Respuesta.class)
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@Validated(Cliente.EventoRegistro.class)
	ResponseEntity<Respuesta> registrarCliente(@RequestBody Cliente cliente) throws NoRegistrableExcepcion;

	@ApiOperation(value = "Actualiza a un cliente con una o varias cuentas bancarias.", response = Respuesta.class)
	@PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@Validated(Cliente.EventoActualizacion.class)
	ResponseEntity<Respuesta> actualizarCliente(@RequestBody Cliente cliente) throws NoActualizableExcepcion;

	@ApiOperation(value = "Elimina a un cliente con todas sus cuentas asociadas.", response = Respuesta.class)
	@DeleteMapping(path = "/{idCliente}", produces = APPLICATION_JSON_VALUE)
	@Validated
	ResponseEntity<Respuesta> eliminarCliente(
			@PathVariable("idCliente") @Positive(message = "El identificador del cliente no puede ser negativo o cero.") int idCliente)
			throws NoEliminableExcepcion;

}
