package com.banco.servicio.cuenta.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.servicio.cuenta.entidad.Cuenta;

@Repository
public interface RepositorioCuenta extends JpaRepository<Cuenta, Integer> {

	List<Cuenta> findAllByIdCliente(int idCliente);

	void deleteAllByIdCliente(int idCliente);

}
