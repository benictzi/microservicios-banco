package com.banco.servicio.cuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = "com.banco.servicio.cuenta")
@EnableJpaRepositories(basePackages = "com.banco.servicio.cuenta.repositorio")
@EnableRetry
@EnableEurekaClient
public class ServicioCuentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioCuentaApplication.class, args);
	}

}
