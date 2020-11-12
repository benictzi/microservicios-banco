package com.banco.servicio.cuenta.servicio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banco.servicio.cuenta.entidad.Cuenta;

@ExtendWith(MockitoExtension.class)
public class ServicioCuentaTest {

	@Mock
	private ServicioCuenta servicioCuenta;

	@Test
	public void servicioCuentaDebeRetornarCuentasPorClienteExistente() {
		List<Cuenta> cuentas = asList(new Cuenta(1, 1, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		when(this.servicioCuenta.obtenerCuentas(1)).thenReturn(cuentas);

		List<Cuenta> cuentasObtenidas = this.servicioCuenta.obtenerCuentas(1);
		assertNotNull(cuentasObtenidas);
		assertEquals(cuentas.size(), cuentasObtenidas.size());

		Cuenta cuentaEsperadaA = cuentas.get(0);
		Cuenta cuentaEsperadaB = cuentas.get(1);

		Cuenta cuentaObtenidaA = cuentas.get(0);
		Cuenta cuentaObtenidaB = cuentas.get(1);

		assertAll("La cuenta A maquetada debe ser igual a la obtenida",
				() -> assertEquals(cuentaEsperadaA.getIdCuenta(), cuentaObtenidaA.getIdCuenta()),
				() -> assertEquals(cuentaEsperadaA.getIdCliente(), cuentaObtenidaA.getIdCliente()),
				() -> assertEquals(cuentaEsperadaA.getSaldo(), cuentaObtenidaA.getSaldo()),
				() -> assertEquals(cuentaEsperadaA.getTipoProducto(), cuentaObtenidaA.getTipoProducto()));

		assertAll("La cuenta B maquetada debe ser igual a la obtenida",
				() -> assertEquals(cuentaEsperadaB.getIdCuenta(), cuentaObtenidaB.getIdCuenta()),
				() -> assertEquals(cuentaEsperadaB.getIdCliente(), cuentaObtenidaB.getIdCliente()),
				() -> assertEquals(cuentaEsperadaB.getSaldo(), cuentaObtenidaB.getSaldo()),
				() -> assertEquals(cuentaEsperadaB.getTipoProducto(), cuentaObtenidaB.getTipoProducto()));
	}

	@Test
	public void servicioCuentaDebeRetornarArregloVacioPorClienteInexistente() {
		final int SIN_DATOS = 0;
		when(this.servicioCuenta.obtenerCuentas(1001010)).thenReturn(Collections.emptyList());
		List<Cuenta> cuentasObtenidas = this.servicioCuenta.obtenerCuentas(1001010);
		assertThat(cuentasObtenidas).hasSize(SIN_DATOS);
	}

	@Test
	public void servicioCuentaRegistraExitosamenteCuentaPorCliente() {
		List<Cuenta> cuentas = asList(new Cuenta(1, 1, 100.01, "Tarjeta debito"),
				new Cuenta(2, 1, 100.21, "Tarjeta credito"));
		when(this.servicioCuenta.registrarCuentas(cuentas)).thenReturn(true);
		boolean cuentasRegistradasCorrectamente = this.servicioCuenta.registrarCuentas(cuentas);
		assertTrue(cuentasRegistradasCorrectamente);
	}

	@Test
	public void servicioCuentaRegistraNoRegistraCuentasPorCliente() {
		List<Cuenta> cuentas = asList(new Cuenta(1, -111, 0.0, "ERROR"), new Cuenta(2, -111, 2000.51, "ERROR"));
		when(this.servicioCuenta.registrarCuentas(cuentas)).thenReturn(false);
		boolean cuentasRegistradasCorrectamente = this.servicioCuenta.registrarCuentas(cuentas);
		assertFalse(cuentasRegistradasCorrectamente);
	}

	@Test
	public void servicioCuentaActualizaExitosamenteCuentaPorCliente() {
		List<Cuenta> cuentas = asList(new Cuenta(1, 1, 180.90, "Tarjeta debito"),
				new Cuenta(2, 1, 300.99, "Tarjeta credito"));
		when(this.servicioCuenta.actualizarCuentas(cuentas)).thenReturn(true);
		boolean cuentasActualizadasCorrectamente = this.servicioCuenta.actualizarCuentas(cuentas);
		assertTrue(cuentasActualizadasCorrectamente);
	}

	@Test
	public void servicioCuentaNoActualizaCuentasPorCliente() {
		List<Cuenta> cuentas = asList(new Cuenta(-1, -1, 180.90, "ERROR"), new Cuenta(-2, 0, 300.99, "ERROR"));
		when(this.servicioCuenta.actualizarCuentas(cuentas)).thenReturn(false);
		boolean cuentasActualizadasCorrectamente = this.servicioCuenta.actualizarCuentas(cuentas);
		assertFalse(cuentasActualizadasCorrectamente);
	}

	@Test
	public void servicioCuentaEliminaExitosamenteCuentasPorCliente() {
		when(this.servicioCuenta.eliminarCuentas(1)).thenReturn(true);
		boolean cuentasEliminadasCorrectamente = this.servicioCuenta.eliminarCuentas(1);
		assertTrue(cuentasEliminadasCorrectamente);
	}
	
	@Test
	public void servicioCuentaNoEliminaCuentasPorClienteInexistente() {
		when(this.servicioCuenta.eliminarCuentas(80808080)).thenReturn(false);
		boolean cuentasEliminadasCorrectamente = this.servicioCuenta.eliminarCuentas(80808080);
		assertFalse(cuentasEliminadasCorrectamente);
	}


}
