package com.banco.servicio.cliente.servicio;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banco.servicio.cliente.dto.Cuenta;
import com.banco.servicio.cliente.entidad.Cliente;

@ExtendWith(MockitoExtension.class)
public class ServicioClienteTest {

	@Mock
	private ServicioCliente servicioCliente;

	@Test
	public void debeObtenerClienteConCuentas() {
		final int ID_CLIENTE = 12;
		List<Cuenta> cuentasEsperadas = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		Cliente clienteEsperado = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez", LocalDate.of(1994, Month.OCTOBER, 24),
				"H", cuentasEsperadas);

		when(this.servicioCliente.obtenerCliente(ID_CLIENTE)).thenReturn(clienteEsperado);

		Cliente clienteObtenido = this.servicioCliente.obtenerCliente(ID_CLIENTE);
		List<Cuenta> cuentasObtenidas = clienteObtenido.getCuentas();
		assertNotNull(clienteObtenido);
		assertAll("Son iguales los datos del cliente y de la cuenta",
				() -> assertThat(clienteEsperado.getIdCliente()).isEqualTo(clienteObtenido.getIdCliente()),
				() -> assertThat(clienteEsperado.getApellidos()).isEqualTo(clienteObtenido.getApellidos()),
				() -> assertThat(clienteEsperado.getFechaNacimiento()).isEqualTo(clienteObtenido.getFechaNacimiento()),
				() -> assertThat(clienteEsperado.getNombre()).isEqualTo(clienteObtenido.getNombre()),
				() -> assertThat(clienteEsperado.getSexo()).isEqualTo(clienteObtenido.getSexo()),
				() -> assertAll("Son datos de la cuenta correctos",
						() -> assertThat(cuentasEsperadas.get(0).getIdCliente())
								.isEqualTo(cuentasObtenidas.get(0).getIdCliente()),
						() -> assertThat(cuentasEsperadas.get(0).getIdCuenta())
								.isEqualTo(cuentasObtenidas.get(0).getIdCuenta()),
						() -> assertThat(cuentasEsperadas.get(0).getSaldo())
								.isEqualTo(cuentasObtenidas.get(0).getSaldo()),
						() -> assertThat(cuentasEsperadas.get(0).getTipoProducto())
								.isEqualTo(cuentasObtenidas.get(0).getTipoProducto())));

	}

	@Test
	public void debeObtenerNullSiClienteEsInexistente() {
		final int ID_CLIENTE = 123_123;
		when(this.servicioCliente.obtenerCliente(ID_CLIENTE)).thenReturn(null);
		Cliente clienteObtenido = this.servicioCliente.obtenerCliente(ID_CLIENTE);
		assertNull(clienteObtenido);
	}

	@Test
	public void debeObtenerTodosLosClientes() {
		final int ID_CLIENTE = 12;

		List<Cuenta> cuentasEsperadas = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		List<Cliente> clientesEsperados = asList(new Cliente(ID_CLIENTE, "Benjamin", "Alvarez",
				LocalDate.of(1994, Month.OCTOBER, 24), "H", cuentasEsperadas));
		Cliente clienteEsperado = clientesEsperados.get(0);

		when(this.servicioCliente.obtenerClientes()).thenReturn(clientesEsperados);

		List<Cliente> clientesObtenidos = this.servicioCliente.obtenerClientes();
		Cliente clienteObtenido = clientesObtenidos.get(0);
		List<Cuenta> cuentasObtenidas = clienteObtenido.getCuentas();

		assertNotNull(clientesObtenidos);
		assertAll("Son iguales los datos del cliente y de la cuenta",
				() -> assertThat(clienteEsperado.getIdCliente()).isEqualTo(clienteObtenido.getIdCliente()),
				() -> assertThat(clienteEsperado.getApellidos()).isEqualTo(clienteObtenido.getApellidos()),
				() -> assertThat(clienteEsperado.getFechaNacimiento()).isEqualTo(clienteObtenido.getFechaNacimiento()),
				() -> assertThat(clienteEsperado.getNombre()).isEqualTo(clienteObtenido.getNombre()),
				() -> assertThat(clienteEsperado.getSexo()).isEqualTo(clienteObtenido.getSexo()),
				() -> assertAll("Son datos de la cuenta correctos",
						() -> assertThat(cuentasEsperadas.get(0).getIdCliente())
								.isEqualTo(cuentasObtenidas.get(0).getIdCliente()),
						() -> assertThat(cuentasEsperadas.get(0).getIdCuenta())
								.isEqualTo(cuentasObtenidas.get(0).getIdCuenta()),
						() -> assertThat(cuentasEsperadas.get(0).getSaldo())
								.isEqualTo(cuentasObtenidas.get(0).getSaldo()),
						() -> assertThat(cuentasEsperadas.get(0).getTipoProducto())
								.isEqualTo(cuentasObtenidas.get(0).getTipoProducto())));

	}

	@Test
	public void debeRetornarArregloClientesVacioIfNoHayDatos() {
		when(this.servicioCliente.obtenerClientes()).thenReturn(new ArrayList<>());
		List<Cliente> clientesObtenidos = this.servicioCliente.obtenerClientes();
		assertNotNull(clientesObtenidos);
		assertThat(clientesObtenidos).hasSize(0);
	}

	@Test
	public void registraClienteExitosamente() {
		final int ID_CLIENTE = 12;
		List<Cuenta> cuentasEsperadas = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		Cliente clienteEsperado = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez", LocalDate.of(1994, Month.OCTOBER, 24),
				"H", cuentasEsperadas);
		when(this.servicioCliente.registrarCliente(clienteEsperado)).thenReturn(true);
		boolean esRegistradoCorrectamente = this.servicioCliente.registrarCliente(clienteEsperado);
		assertTrue(esRegistradoCorrectamente);
	}

	@Test
	public void noRegistraClienteExitosamente() {
		final int ID_CLIENTE = 12;
		List<Cuenta> cuentasEsperadas = asList(new Cuenta(1, 00000, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		Cliente clienteEsperado = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez", LocalDate.of(1994, Month.OCTOBER, 24),
				"H", cuentasEsperadas);
		when(this.servicioCliente.registrarCliente(clienteEsperado)).thenReturn(false);
		boolean esRegistradoCorrectamente = this.servicioCliente.registrarCliente(clienteEsperado);
		assertFalse(esRegistradoCorrectamente);
	}

	@Test
	public void actualizaClienteExitosamente() {
		final int ID_CLIENTE = 12;
		List<Cuenta> cuentasEsperadas = asList(new Cuenta(1, ID_CLIENTE, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		Cliente clienteEsperado = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez", LocalDate.of(1994, Month.OCTOBER, 24),
				"H", cuentasEsperadas);
		when(this.servicioCliente.actualizarCliente(clienteEsperado)).thenReturn(true);
		boolean esActualizadoCorrectamente = this.servicioCliente.actualizarCliente(clienteEsperado);
		assertTrue(esActualizadoCorrectamente);
	}

	@Test
	public void noActualizaClienteExitosamente() {
		final int ID_CLIENTE = 0000;
		List<Cuenta> cuentasEsperadas = asList(new Cuenta(1, 00000, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		Cliente clienteEsperado = new Cliente(ID_CLIENTE, "Benjamin", "Alvarez", LocalDate.of(1994, Month.OCTOBER, 24),
				"H", cuentasEsperadas);
		when(this.servicioCliente.actualizarCliente(clienteEsperado)).thenReturn(false);
		boolean esActualizadoCorrectamente = this.servicioCliente.actualizarCliente(clienteEsperado);
		assertFalse(esActualizadoCorrectamente);
	}

	@Test
	public void eliminaExitosamenteCliente() {
		when(this.servicioCliente.eliminarCliente(1)).thenReturn(true);
		boolean clienteEliminado = this.servicioCliente.eliminarCliente(1);
		assertTrue(clienteEliminado);
	}

	@Test
	public void noEliminaExitosamenteCliente() {
		when(this.servicioCliente.eliminarCliente(121_212_122)).thenReturn(false);
		boolean clienteEliminado = this.servicioCliente.eliminarCliente(121_212_122);
		assertFalse(clienteEliminado);
	}

}
