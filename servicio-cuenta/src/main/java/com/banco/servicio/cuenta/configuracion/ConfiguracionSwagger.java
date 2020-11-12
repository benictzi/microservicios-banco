package com.banco.servicio.cuenta.configuracion;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class ConfiguracionSwagger  {
	
	@Bean
	public Docket productApi() {
		final Set<String> TIPOS_CONTENIDO_SOPORTADOS = new HashSet<String>();
		TIPOS_CONTENIDO_SOPORTADOS.add(APPLICATION_JSON_VALUE);
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.banco.servicio.cuenta.controlador"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
				.genericModelSubstitutes(ResponseEntity.class)
				.produces(TIPOS_CONTENIDO_SOPORTADOS)
				.consumes(TIPOS_CONTENIDO_SOPORTADOS)
				.apiInfo(apiInfo());
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfo("Microservicio REST Cuenta", 
				"API encargada de consultar las cuentas por cliente, actualizar las cuentas por cliente, eliminar cuentas por cliente y crear cuentas por cliente", 
				"API", null,
				"beni.ctzi@gmail.com", 
				null, 
				null);
	}
}
