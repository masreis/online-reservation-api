package net.onlinereservation.entity;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation implements Serializable {

	private static final long serialVersionUID = 5366913593224700256L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Email
	private String email;

	@NotBlank
	private String fullName;

	@Temporal(TemporalType.DATE)
	@NotNull
	@FutureOrPresent
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@NotNull
	@FutureOrPresent
	private Date endDate;

	@NotNull
	private Hotel hotel;

	@NotNull
	private Currency pricePerDay;

}
