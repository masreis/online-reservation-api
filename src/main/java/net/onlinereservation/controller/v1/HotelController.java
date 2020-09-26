package net.onlinereservation.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.onlinereservation.dto.HotelDTO;
import net.onlinereservation.dto.Response;
import net.onlinereservation.entity.Hotel;
import net.onlinereservation.service.HotelService;

@RestController
@RequestMapping("/v1/hotels")
@Api("This is the Hotel Controller")
public class HotelController {

	private HotelService hotelService;

	private ModelMapper modelMapper;

	@Autowired
	public HotelController(HotelService hotelService, ModelMapper modelMapper) {
		this.hotelService = hotelService;
		this.modelMapper = modelMapper;
	}

	/**
	 * 
	 * @param dto
	 * @param result
	 * @return
	 * 
	 */

	@PostMapping
	@ApiOperation("Creates a hotel in the database")
	public ResponseEntity<Response<HotelDTO>> create(@Valid @RequestBody HotelDTO dto, BindingResult result) {
		Response<HotelDTO> response = new Response<>();
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addError(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Hotel hotel = hotelService.save(modelMapper.map(dto, Hotel.class));
		HotelDTO hotelDto = modelMapper.map(hotel, HotelDTO.class);
		response.setData(hotelDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	@ApiOperation("Returns the list of hotels")
	public ResponseEntity<Response<List<HotelDTO>>> findAll() {
		Response<List<HotelDTO>> response = new Response<List<HotelDTO>>();
		List<HotelDTO> hotelsDto = this.hotelService.findAll().stream()
				.map(hotel -> modelMapper.map(hotel, HotelDTO.class)).collect(Collectors.toList());
		response.setData(hotelsDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
