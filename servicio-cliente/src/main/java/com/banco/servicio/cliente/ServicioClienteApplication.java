package com.banco.servicio.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = "com.banco.servicio.cliente")
@EnableJpaRepositories(basePackages = "com.banco.servicio.cliente.repositorio")
@EnableFeignClients(basePackages = "com.banco.servicio.cliente.http")
@EnableRetry
@EnableDiscoveryClient
public class ServicioClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioClienteApplication.class, args);
	}

}
