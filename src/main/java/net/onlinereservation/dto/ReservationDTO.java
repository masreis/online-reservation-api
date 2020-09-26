package net.onlinereservation.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReservationDTO extends RepresentationModel<ReservationDTO> {

	@NotNull
	private Long id;

	@Email
	private String email;

	@NotBlank
	private String fullName;

	@NotNull
	@FutureOrPresent
	private Date startDate;

	@NotNull
	@FutureOrPresent
	private Date endDate;

	@NotNull
	private HotelDTO hotel;

	@NotNull
	private BigDecimal pricePerDay;

}
