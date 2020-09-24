package net.onlinereservation.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Hotel implements Serializable {

	private static final long serialVersionUID = -3687243412118443969L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String email;

	@NotBlank
	private String name;

	@NotBlank
	private String address;

	@NotBlank
	private String phoneNumber;

	@NotNull
	private Integer numberOfRooms;

}
