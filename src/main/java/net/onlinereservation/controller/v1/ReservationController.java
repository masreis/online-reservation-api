package net.onlinereservation.controller.v1;

import java.util.Date;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.onlinereservation.dto.ReservationDTO;
import net.onlinereservation.entity.Hotel;
import net.onlinereservation.entity.Reservation;
import net.onlinereservation.exception.HotelNotFoundException;
import net.onlinereservation.exception.PeriodNotAvailableException;
import net.onlinereservation.exception.ReservationNotFoundException;
import net.onlinereservation.response.Response;
import net.onlinereservation.service.HotelService;
import net.onlinereservation.service.ReservationService;

@Api("This is the Reservation Controller")
@RestController
@RequestMapping("/v1/reservations")
public class ReservationController {

    @Value("${page.size}")
    private int pageSize;

    private ReservationService reservationService;
    private HotelService hotelService;
    private ModelMapper modelMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, HotelService hotelService,
            ModelMapper modelMapper) {
        this.reservationService = reservationService;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation("Verify if the reservation period is available")
    @GetMapping("/available")
    public ResponseEntity<Response<Boolean>> isAvailable(
            @RequestParam(name = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(name = "idHotel", required = true) Long idHotel) throws HotelNotFoundException {

        Response<Boolean> response = new Response<>();

        Hotel hotel = hotelService.findById(idHotel);

        if (this.reservationService.isAvailable(startDate, endDate, hotel)) {
            response.setData(true);
            return ResponseEntity.ok(response);
        } else {
            response.setData(false);
            return new ResponseEntity<Response<Boolean>>(response, HttpStatus.CONFLICT);
        }

    }

    @ApiOperation("Retrieve all reservations")
    @GetMapping
    // TODO Add sorting options
    public ResponseEntity<Response<Page<ReservationDTO>>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pg = PageRequest.of(page, pageSize);
        Response<Page<ReservationDTO>> response = new Response<Page<ReservationDTO>>();
        Page<ReservationDTO> reservations = null;
        reservations = reservationService.findAll(pg)
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class));
        response.setData(reservations);
        return ResponseEntity.ok(response);

    }

    @ApiOperation("Retrieve the reservation by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response<ReservationDTO>> findById(@PathParam("id") Long id)
            throws ReservationNotFoundException {

        Response<ReservationDTO> response = new Response<ReservationDTO>();
        Reservation reservation = reservationService.findById(id);
        response.setData(modelMapper.map(reservation, ReservationDTO.class));
        return ResponseEntity.ok(response);

    }

    @ApiOperation("Retrieve all reservations by date")
    @GetMapping("/findAllByDate")
    // TODO Add sorting options
    public ResponseEntity<Response<Page<ReservationDTO>>> findAllByDate(
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pg = PageRequest.of(page, pageSize);
        Response<Page<ReservationDTO>> response = new Response<Page<ReservationDTO>>();
        Page<ReservationDTO> reservations = null;
        reservations = reservationService.findAllByDate(startDate, endDate, pg)
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class));
        response.setData(reservations);
        return ResponseEntity.ok(response);

    }

    @ApiOperation("Insert a new reservation")
    @PostMapping
    public ResponseEntity<Response<ReservationDTO>> create(@Valid @RequestBody ReservationDTO dto, BindingResult result)
            throws PeriodNotAvailableException {

        Response<ReservationDTO> response = new Response<ReservationDTO>();

        if (!reservationService.isAvailable(dto.getStartDate(), dto.getEndDate(),
                modelMapper.map(dto.getHotel(), Hotel.class))) {
            throw new PeriodNotAvailableException("The selected period is not available");
        }

        Reservation reservation = reservationService.save(modelMapper.map(dto, Reservation.class));
        response.setData(modelMapper.map(reservation, ReservationDTO.class));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a reservation")
    @PutMapping("/{id}")
    public ResponseEntity<Response<ReservationDTO>> update(@PathVariable("id") final Long id,
            @Valid @RequestBody ReservationDTO dto, BindingResult result)
            throws ReservationNotFoundException, PeriodNotAvailableException {

        Response<ReservationDTO> response = new Response<ReservationDTO>();

        if (reservationService.existsById(id)) {

            // TODO Verificar
            dto.setId(id);
            Reservation reservationSaved = reservationService.save(modelMapper.map(dto, Reservation.class));
            response.setData(modelMapper.map(reservationSaved, ReservationDTO.class));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @ApiOperation("Delete a reservation")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        Response<String> response = new Response<String>();
        if (reservationService.existsById(id)) {
            reservationService.deleteById(id);
            response.setData("Reservation " + id + " deleted.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response.setData("Reservation " + id + " does not exist.");
            return new ResponseEntity<Response<String>>(response, HttpStatus.NOT_FOUND);
        }
    }

//	private void validate(ReservationDTO dto) {
//		validateArrivalTime(startDate);
//		validateStartDateBeforeEndDate(startDate, endDate);
//	}
//
//	public void validateStartDateBeforeEndDate(Date startDate, Date endDate) {
//		if (startDate.after(endDate)) {
//			throw new StartDateBeforeEndDateException("The arrival date should be prior the end date");
//		}
//	}
//
//	public void validateArrivalTime(Date startDate) throws OnlineReservationException {
//		Date minArrivalTime = DateUtils.addDays(new Date(), 1);
//		Date maxArrivalTime = DateUtils.addMonths(new Date(), 1);
//		long differenceMin = TimeUnit.DAYS.convert(startDate.getTime() - minArrivalTime.getTime(),
//				TimeUnit.MILLISECONDS);
//		if (differenceMin < 0) {
//			throw new OnlineReservationException("The minimum arrival time is 1 day");
//		}
//		if (startDate.after(maxArrivalTime)) {
//			throw new OnlineReservationException("The maximum arrival time is 1 month");
//		}
//	}

}
