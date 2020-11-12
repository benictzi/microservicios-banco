package com.banco.servicio.cuenta.controlador.impl;

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

import com.banco.servicio.cuenta.dto.Respuesta;
import com.banco.servicio.cuenta.entidad.Cuenta;
import com.banco.servicio.cuenta.excepcion.ErrorInternoExcepcion;
import com.banco.servicio.cuenta.excepcion.NoActualizableExcepcion;
import com.banco.servicio.cuenta.excepcion.NoEliminableExcepcion;
import com.banco.servicio.cuenta.excepcion.NoRegistrableExcepcion;
import com.banco.servicio.cuenta.servicio.ServicioCuenta;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ControladorCuentaTest {

	private MockMvc mockMvc;

	private ObjectMapper mapper;

	@Mock
	private ServicioCuenta servicioCuenta;

	@InjectMocks
	private ControladorCuentaImpl controladorCuenta;

	private JacksonTester<List<Cuenta>> jacksonTesterCuenta;

	private JacksonTester<Respuesta> jacksonTesterRespuesta;

	@BeforeEach
	public void setup() {
		this.mapper = new ObjectMapper();
		JacksonTester.initFields(this, new ObjectMapper());
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controladorCuenta)
				.setControllerAdvice(new ControladorExcepcionGlobal()).build();
	}

	@Test
	public void obtenerCuentasPorIdClienteExitosamente() throws Exception {
		final int ID_CLIENTE = 1;
		final List<Cuenta> CUENTAS = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, ID_CLIENTE, 100.21, "Tarjeta credito"));

		given(this.servicioCuenta.obtenerCuentas(ID_CLIENTE)).willReturn(CUENTAS);

		MockHttpServletResponse respuesta = this.mockMvc
				.perform(get("/cuentas/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)).andReturn().getResponse();

		assertAll("Retorna correctamente todas las cuentas por cliente",
				() -> assertThat(respuesta.getStatus()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(respuesta.getContentAsString())
						.isEqualTo(this.jacksonTesterCuenta.write(CUENTAS).getJson()));

	}

	@Test
	public void lanzaErrorInternoExcepcionAlObtenerCuentasPorCliente() throws Exception {
		final int ID_CLIENTE = 100;
		given(this.servicioCuenta.obtenerCuentas(ID_CLIENTE))
				.willThrow(new RuntimeException("ERROR EN TIEMPO DE EJECUSION NO ESPERADO"));

		this.mockMvc
				.perform(get("/cuentas/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ErrorInternoExcepcion))
				.andExpect(result -> assertEquals(
						"Ocurrio un error al consultar las cuentas del cliente con identificador " + ID_CLIENTE,
						result.getResolvedException().getMessage()));

	}

	@Test
	public void registrarCuentasPorClienteExistosamente() throws Exception {
		final int ID_CLIENTE = 1;
		final List<Cuenta> CUENTAS = asList(new Cuenta(1, ID_CLIENTE, 450.34, "Tarjeta debito"),
				new Cuenta(2, ID_CLIENTE, 99.99, "Tarjeta credito"));

		final Respuesta respuestaEsperada = new Respuesta("Se registraron correctamente las cuentas", true);

		given(this.servicioCuenta.registrarCuentas(CUENTAS)).willReturn(true);

		MockHttpServletResponse respuesta = this.mockMvc.perform(post("/cuentas").contentType(APPLICATION_JSON_VALUE)
				.accept(APPLICATION_JSON_VALUE).content(this.mapper.writeValueAsString(CUENTAS))).andReturn()
				.getResponse();

		assertAll("Registra correctamente todas las cuentas por cliente",
				() -> assertThat(respuesta.getStatus()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(respuesta.getContentAsString())
						.isEqualTo(this.jacksonTesterRespuesta.write(respuestaEsperada).getJson()));
	}

	@Test
	public void lanzaNoRegistrableExcepcionAlRegistrarCuentasPorCliente() throws Exception {
		final int ID_CLIENTE = 65;
		final List<Cuenta> CUENTAS = asList(new Cuenta(1, ID_CLIENTE, 450.34, "Tarjeta debito"),
				new Cuenta(2, ID_CLIENTE, 99.99, "Tarjeta credito"));

		given(this.servicioCuenta.registrarCuentas(CUENTAS)).willThrow(new RuntimeException("ERROR NO ESPERADO"));

		this.mockMvc
				.perform(post("/cuentas").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(CUENTAS)))
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoRegistrableExcepcion))
				.andExpect(result -> assertEquals("Ocurrio un error al registrar las cuentas.",
						result.getResolvedException().getMessage()));

	}

	@Test
	public void lanzaBadRequestAlRegistrarCuentasPorCliente() throws Exception {
		this.mockMvc.perform(post("/cuentas").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void actualizarCuentasPorClienteExistosamente() throws Exception {
		final int ID_CLIENTE = 1;
		final List<Cuenta> CUENTAS = asList(new Cuenta(1, ID_CLIENTE, 450.34, "Tarjeta debito"),
				new Cuenta(2, ID_CLIENTE, 99.99, "Tarjeta credito"));

		final Respuesta RESPUESTA_ESPERADA = new Respuesta("Se actualizaron correctamente las cuentas", true);

		given(this.servicioCuenta.actualizarCuentas(CUENTAS)).willReturn(true);

		MockHttpServletResponse respuesta = this.mockMvc.perform(put("/cuentas").contentType(APPLICATION_JSON_VALUE)
				.accept(APPLICATION_JSON_VALUE).content(this.mapper.writeValueAsString(CUENTAS))).andReturn()
				.getResponse();

		assertAll("Actualiza correctamente todas las cuentas por cliente",
				() -> assertThat(respuesta.getStatus()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(respuesta.getContentAsString())
						.isEqualTo(this.jacksonTesterRespuesta.write(RESPUESTA_ESPERADA).getJson()));
	}

	@Test
	public void lanzaNoActualizableExcepcionAlActualizarCuentasPorCliente() throws Exception {
		final int ID_CLIENTE = 65;
		final List<Cuenta> cuentas = asList(new Cuenta(1, ID_CLIENTE, 450.34, "Tarjeta debito"),
				new Cuenta(2, ID_CLIENTE, 99.99, "Tarjeta credito"));

		given(this.servicioCuenta.actualizarCuentas(cuentas)).willThrow(new RuntimeException("ERROR NO ESPERADO"));

		this.mockMvc
				.perform(put("/cuentas").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(cuentas)))
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoActualizableExcepcion))
				.andExpect(result -> assertEquals("Ocurrio un error al actualizar las cuentas.",
						result.getResolvedException().getMessage()));

	}

	@Test
	public void lanzaBadRequestAlActualizarCuentasPorCliente() throws Exception {
		this.mockMvc.perform(put("/cuentas").accept(APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void eliminaCuentasPorIdClienteExitosamente() throws Exception {
		final int ID_CLIENTE = 1;
		final Respuesta RESPUESTA_ESPERADA = new Respuesta("Se eliminaron correctamente las cuentas", true);
		given(this.servicioCuenta.eliminarCuentas(ID_CLIENTE)).willReturn(true);

		MockHttpServletResponse respuesta = this.mockMvc
				.perform(delete("/cuentas/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)).andReturn().getResponse();

		assertAll("Elimina correctamente todas las cuentas por cliente",
				() -> assertThat(respuesta.getStatus()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(respuesta.getContentAsString())
						.isEqualTo(this.jacksonTesterRespuesta.write(RESPUESTA_ESPERADA).getJson()));

	}

	@Test
	public void lanzaNoEliminableExcepcionAlEliminarCuentasPorCliente() throws Exception {
		final int ID_CLIENTE = 154;

		given(this.servicioCuenta.eliminarCuentas(ID_CLIENTE)).willThrow(new RuntimeException("ERROR NO ESPERADO"));

		this.mockMvc
				.perform(delete("/cuentas/" + ID_CLIENTE).accept(APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isServiceUnavailable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoEliminableExcepcion))
				.andExpect(result -> assertEquals(
						"Ocurrio un error al intentar eliminar las cuentas del cliente con identificador " + ID_CLIENTE,
						result.getResolvedException().getMessage()));
	}

}
