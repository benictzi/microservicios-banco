package com.banco.servicio.cliente.entidad;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.banco.servicio.cliente.dto.Cuenta;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Clase que representa a un cliente en la aplicacion.")
@Entity
@Table(name = "CLIENTE")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {

	public static interface EventoRegistro {
	}

	public static interface EventoActualizacion {
	}

	@ApiModelProperty(notes = "Identificador del cliente.", example = "12")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Positive(message = "El identificador del cliente no puede ser un numero negativo.", groups = EventoActualizacion.class)
	@Max(message = "El identificador del cliente sobrepasa al permitido, comuniquese con el administrador del sistema.", value = Integer.MAX_VALUE, groups = EventoActualizacion.class)
	private int idCliente;

	@ApiModelProperty(notes = "Nombre del cliente.", example = "Benjamin", required = true)
	@NotNull(message = "El nombre no puede ser nulo o vacio", groups = { EventoActualizacion.class,
			EventoRegistro.class })
	@Size(min = 1, max = 60, message = "El nombre puede tener solo de {min} a {max} caracteres", groups = {
			EventoActualizacion.class, EventoRegistro.class })
	@Column(name = "NOMBRE")
	private String nombre;

	@ApiModelProperty(notes = "Apellidos del cliente.", example = "Ocotzi Alvarez", required = true)
	@NotNull(message = "Los apellidos no pueden ser nulos o vacios", groups = { EventoActualizacion.class,
			EventoRegistro.class })
	@Size(min = 1, max = 120, message = "Los apellidos pueden tener solo de {min} a {max} caracteres", groups = {
			EventoActualizacion.class, EventoRegistro.class })
	@Column(name = "APELLIDOS")
	private String apellidos;

	@ApiModelProperty(notes = "Fecha de nacimiento del cliente", example = "1993-10-24", required = true)
	@Column(name = "FECHA_NACIMIENTO")
	@PastOrPresent(message = "La fecha de nacimiento no puede ser futura", groups = { EventoActualizacion.class,
			EventoRegistro.class })
	@JsonSerialize(using = ToStringSerializer.class) 
	private LocalDate fechaNacimiento;

	@ApiModelProperty(notes = "Sexo biologico del cliente.", example = "H", required = true)
	@Pattern(regexp = "H|M|", message = "Solo puede colocar H para hombre o M para mujer", groups = {
			EventoActualizacion.class, EventoRegistro.class })
	@Column(name = "SEXO")
	private String sexo;

	@ApiModelProperty(notes = "Cuentas asociadas a el cliente.")
	@Transient
	private List<Cuenta> cuentas;
}
