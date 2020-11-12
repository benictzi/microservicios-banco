package com.banco.servicio.cliente.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Clase que representa una cuenta bancaria en la aplicacion.")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {

	@ApiModelProperty(notes = "Identificador de la cuenta bancaria.", example = "456", required = true)
	private int idCuenta;

	@ApiModelProperty(notes = "Identificador del cliente.", example = "128", required = true)
	private int idCliente;

	@ApiModelProperty(notes = "Cantidad monetaria que contiene la cuenta.", example = "1400.94", required = true)
	private double saldo;

	@ApiModelProperty(notes = "Tipo de producto de la cuenta bancaria.", example = "Tarjeta de debito", required = true)
	private String tipoProducto;

}
