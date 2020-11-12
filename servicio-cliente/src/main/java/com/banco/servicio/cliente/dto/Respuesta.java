package com.banco.servicio.cliente.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Clase que representa una respuesta de una accion en la aplicacion.")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Respuesta {

	@ApiModelProperty(notes = "Mensaje generico con informacion de la accion realizada.")
	private String mensaje;

	@ApiModelProperty(notes = "Bandera que indica si la accion realizada fue exitosa.")
	private boolean operacionCompletada;

}
