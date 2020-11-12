package com.banco.servicio.cuenta.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Clase que representa una cuenta bancaria en la aplicacion.")
@Entity
@Table(name = "CUENTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {

	public static interface EventoRegistro {
	}

	public static interface EventoActualizacion {
	}

	@ApiModelProperty(notes = "Identificador de la cuenta bancaria.", example = "456", required = true)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CUENTA")
	@Positive(message = "El identificador de la cuenta no puede ser un numero negativo.", groups = EventoActualizacion.class)
	@Max(message = "El identificador de la cuenta sobrepasa al permitido, comuniquese con el administrador del sistema.", value = Integer.MAX_VALUE, groups = EventoActualizacion.class)
	private int idCuenta;

	@ApiModelProperty(notes = "Identificador del cliente.", example = "128", required = true)
	@Column(name = "ID_CLIENTE")
	@NotNull(message = "El valor del identificador del cliente no puede ser nulo.", groups = { EventoRegistro.class,
			EventoActualizacion.class })
	@Positive(message = "El identificador del cliente no puede ser un numero negativo o cero.", groups = {
			EventoRegistro.class, EventoActualizacion.class })
	@Max(message = "El identificador del cliente ha llegado al maximo, comuniquese con el administrador del sistema.", value = Integer.MAX_VALUE, groups = {
			EventoRegistro.class, EventoActualizacion.class })
	private int idCliente;

	@ApiModelProperty(notes = "Cantidad monetaria que contiene la cuenta.", example = "1400.94", required = true)
	@Column(name = "SALDO")
	@NotNull(message = "El saldo no puede ser nulo.", groups = { EventoRegistro.class, EventoActualizacion.class })
	@DecimalMin(value = "0.0", inclusive = true, message = "El saldo minimo debe ser 0.0, no puede ser negativo.", groups = {
			EventoRegistro.class, EventoActualizacion.class })
	@Digits(integer = 9, fraction = 9, message = "El saldo permitido debe tener de 1 a 9 digitos tanto en la parte entera como fraccionaria.")
	private double saldo;

	@ApiModelProperty(notes = "Tipo de producto de la cuenta bancaria.", example = "Tarjeta de debito", required = true)
	@Column(name = "TIPO_PRODUCTO")
	@NotNull(message = "El tipo de producto no puede ser nulo.", groups = { EventoRegistro.class,
			EventoActualizacion.class })
	@Size(min = 0, max = 120, message = "El tipo de producto debe contener de 0 a 120 caracteres", groups = {
			EventoRegistro.class, EventoActualizacion.class })
	private String tipoProducto;

}
