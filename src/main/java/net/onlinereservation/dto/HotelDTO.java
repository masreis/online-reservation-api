package net.onlinereservation.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HotelDTO {

	private Long id;

	@NotBlank(message = "Email must not be blank")
	private String email;

	@NotBlank(message = "FullName must not be blank")
	private String fullName;

}
