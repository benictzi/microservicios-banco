package com.banco.servicio.cliente.http;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.banco.servicio.cliente.dto.Cuenta;
import com.banco.servicio.cliente.dto.Respuesta;

@FeignClient(url = "${servicios.cuenta.url}", name = "${servicios.cuenta.id}")
public interface ClienteHttpCuenta {

	@GetMapping("/cuentas/{idCliente}")
	List<Cuenta> obtenerCuentas(@PathVariable("idCliente") int idCliente);

	@PostMapping("/cuentas")
	Respuesta registrarCuentas(@RequestBody List<Cuenta> cuentas);

	@PutMapping("/cuentas")
	Respuesta actualizarCuentas(@RequestBody List<Cuenta> cuentas);

	@DeleteMapping("/cuentas/{idCliente}")
	Respuesta eliminarCuentas(@PathVariable("idCliente") int idCliente);

}
