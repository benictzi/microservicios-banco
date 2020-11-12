package com.banco.servicio.cliente.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(description = "Clase que representa una respuesta de error en la aplicacion.")
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Falla {

	@ApiModelProperty(notes = "Mensaje generico con informacion del error.")
	private String mensaje;

	@ApiModelProperty(notes = "Cadena de errores retornada.")
	private List<String> errores;

	@ApiModelProperty(notes = "Codigo numerico del estado http del error.")
	private int codigoEstadoHttp;

	@ApiModelProperty(notes = "Nombre del estado http del error.")
	private String estadoHttp;
}
