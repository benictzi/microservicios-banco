package com.banco.servicio.cliente.controlador.impl;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banco.servicio.cliente.dto.Cuenta;
import com.banco.servicio.cliente.dto.Respuesta;
import com.banco.servicio.cliente.entidad.Cliente;
import com.banco.servicio.cliente.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cliente.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cliente.excepcion.NoEliminableExcepcion;
import com.banco.servicio.cliente.excepcion.NoRegistrableExcepcion;
import com.banco.servicio.cliente.servicio.ServicioCliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class ControladorClienteTest {

	private MockMvc mockMvc;

	private ObjectMapper mapper;

	@Mock
	private ServicioCliente servicioCliente;

	@InjectMocks
	private ControladorClienteImpl controladorCliente;

	private JacksonTester<Cliente> jacksonTesterCliente;

	private JacksonTester<List<Cliente>> jacksonTesterListaClientes;

	private JacksonTester<Respuesta> jacksonTesterRespuesta;

	@BeforeEach
	public void setup() {
		SimpleModule module = new SimpleModule();
		this.mapper = new ObjectMapper();
		this.mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(module);
		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controladorCliente)
				.setControllerAdvice(new ControladorExcepcionGlobal()).build();
	}

	@Test
	public void obtenerCliente() throws Exception {
		final int ID_CLIENTE = 1;
		final List<Cuenta> CUENTAS_ESPERADAS = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		final Cliente CLIENTE_ESPERADO = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez",
				LocalDate.of(1994, Month.OCTOBER, 24), "H", CUENTAS_ESPERADAS);

		given(this.servicioCliente.obtenerCliente(ID_CLIENTE)).willReturn(CLIENTE_ESPERADO);

		MockHttpServletResponse respuesta = this.mockMvc
				.perform(get("/clientes/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)).andReturn().getResponse();

		assertAll("Retorna correctamente un cliente especificado",
				() -> assertThat(respuesta.getStatus()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(respuesta.getContentAsString())
						.isEqualTo(this.jacksonTesterCliente.write(CLIENTE_ESPERADO).getJson()));

	}

	@Test
	public void lanzaErrorInternoExcepcionCuandoSurgeUnErrorNoEsperado() throws Exception {
		final int ID_CLIENTE = 111_111;
		this.mockMvc
				.perform(get("/clientes/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ErrorInternoExcepcion))
				.andExpect(result -> assertEquals(
						"Ocurrio un error al consultar el cliente con identificador " + ID_CLIENTE,
						result.getResolvedException().getMessage()));
	}

	@Test
	public void registraExitosamenteCliente() throws Exception {
		final int ID_CLIENTE = 1;
		final List<Cuenta> CUENTAS_ESPERADAS = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		final Cliente CLIENTE_ESPERADO = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez",
				LocalDate.of(1994, Month.OCTOBER, 24), "H", CUENTAS_ESPERADAS);
		final Respuesta RESPUESTA_ESPERADA = new Respuesta("Se ha registrado correctamente el cliente.", true);

		given(this.servicioCliente.registrarCliente(CLIENTE_ESPERADO)).willReturn(true);

		this.mockMvc
				.perform(post("/clientes").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(CLIENTE_ESPERADO)))
				.andExpect(status().isOk()).andExpect(result -> assertThat(result.getResponse().getContentAsString())
						.isEqualTo(this.jacksonTesterRespuesta.write(RESPUESTA_ESPERADA).getJson()));
	}

	@Test
	public void lanzaNoRegistrableExcepcionAlRegistrarCliente() throws Exception {
		final int ID_CLIENTE = 112_123;
		final List<Cuenta> CUENTAS_ESPERADAS = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		final Cliente CLIENTE_ESPERADO = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez",
				LocalDate.of(1994, Month.OCTOBER, 24), "H", CUENTAS_ESPERADAS);

		given(this.servicioCliente.registrarCliente(CLIENTE_ESPERADO))
				.willThrow(new RuntimeException("ERROR NO ESPERADO"));
		this.mockMvc
				.perform(post("/clientes").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(CLIENTE_ESPERADO)))
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoRegistrableExcepcion))
				.andExpect(result -> assertEquals("Ocurrio un error al registrar el cliente.",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void lanzaBadRequestAlRegistrarCliente() throws Exception {
		this.mockMvc.perform(post("/clientes").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void actualizaExitosamenteCliente() throws Exception {
		final int ID_CLIENTE = 1;
		final List<Cuenta> CUENTAS_ESPERADAS = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		final Cliente CLIENTE_ESPERADO = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez",
				LocalDate.of(1994, Month.OCTOBER, 24), "H", CUENTAS_ESPERADAS);
		final Respuesta RESPUESTA_ESPERADA = new Respuesta("Se ha actualizado correctamente el cliente.", true);

		given(this.servicioCliente.actualizarCliente(CLIENTE_ESPERADO)).willReturn(true);

		this.mockMvc
				.perform(put("/clientes").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(CLIENTE_ESPERADO)))
				.andExpect(status().isOk()).andExpect(result -> assertThat(result.getResponse().getContentAsString())
						.isEqualTo(this.jacksonTesterRespuesta.write(RESPUESTA_ESPERADA).getJson()));
	}

	@Test
	public void lanzaNoActualizableExcepcionAlActualizarCliente() throws Exception {
		final int ID_CLIENTE = 112_123;
		final List<Cuenta> CUENTAS_ESPERADAS = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		final Cliente CLIENTE_ESPERADO = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez",
				LocalDate.of(1994, Month.OCTOBER, 24), "H", CUENTAS_ESPERADAS);

		given(this.servicioCliente.actualizarCliente(CLIENTE_ESPERADO))
				.willThrow(new RuntimeException("ERROR NO ESPERADO"));
		this.mockMvc
				.perform(put("/clientes").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(CLIENTE_ESPERADO)))
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoActualizableExcepcion))
				.andExpect(result -> assertEquals("Ocurrio un error al actualizar el cliente.",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void lanzaBadRequestAlActualizarCliente() throws Exception {
		this.mockMvc.perform(put("/clientes").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void eliminaClienteExitosamente() throws Exception {
		final int ID_CLIENTE = 1;
		final Respuesta RESPUESTA_ESPERADA = new Respuesta("Se ha eliminado correctamente el cliente.", true);
		given(this.servicioCliente.eliminarCliente(ID_CLIENTE)).willReturn(true);

		MockHttpServletResponse respuesta = this.mockMvc
				.perform(delete("/clientes/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)).andReturn().getResponse();

		assertAll("Elimina correctamente todas las cuentas por cliente",
				() -> assertThat(respuesta.getStatus()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(respuesta.getContentAsString())
						.isEqualTo(this.jacksonTesterRespuesta.write(RESPUESTA_ESPERADA).getJson()));

	}

	@Test
	public void lanzaNoEliminableExcepcionAlEliminarCliente() throws Exception {
		final int ID_CLIENTE = 154;

		given(this.servicioCliente.eliminarCliente(ID_CLIENTE)).willThrow(new RuntimeException("ERROR NO ESPERADO"));

		this.mockMvc
				.perform(delete("/clientes/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEliminableExcepcion))
				.andExpect(result -> assertEquals(
						"Ocurrio un error al intentar eliminar el cliente " + ID_CLIENTE,
						result.getResolvedException().getMessage()));
	}

}
