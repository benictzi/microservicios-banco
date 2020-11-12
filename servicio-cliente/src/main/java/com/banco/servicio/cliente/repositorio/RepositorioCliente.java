package com.banco.servicio.cliente.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.servicio.cliente.entidad.Cliente;

@Repository
public interface RepositorioCliente extends JpaRepository<Cliente, Integer> {

}
