package net.onlinereservation.it;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import net.onlinereservation.entity.Hotel;
import net.onlinereservation.entity.Reservation;
import net.onlinereservation.exception.HotelNotFoundException;
import net.onlinereservation.exception.PeriodNotAvailableException;
import net.onlinereservation.exception.ReservationNotFoundException;
import net.onlinereservation.service.HotelService;
import net.onlinereservation.service.ReservationService;

@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class OnlineReservationServiceIT {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private HotelService hotelService;

	@Test
	@Order(1)
	public final void testSave() {
		Long id = null;
		String email = "hotel@hotel.com";
		String name = "My Hotel";
		String address = "My Address";
		String phoneNumber = "555-5555";
		Integer numberOfRooms = 10;
		Hotel hotel = new Hotel(id, email, name, address, phoneNumber, numberOfRooms);
		Hotel savedHotel = hotelService.save(hotel);
		assertTrue(savedHotel.getId() != null);
	}

	@Test
	@Order(2)
	public final void testCache() throws HotelNotFoundException {
		Hotel hotel1 = hotelService.findById(1l);
		Hotel hotel2 = hotelService.findById(1l);
		assertTrue(hotel1 == hotel2);
	}

	@Test
	@Order(3)
	public void testIsAvailable() {
		List<Hotel> hotels = hotelService.findAll();
		assertTrue(hotels.size() > 0);
		Hotel hotel = hotels.get(0);
		Date startDate = DateUtils.addDays(new Date(), 2);
		Date endDate = DateUtils.addDays(new Date(), 3);
		boolean isAvailable = reservationService.isAvailable(startDate, endDate, hotel);
		assertTrue(isAvailable);
	}

	@Test
	@Order(4)
	public void testInsert() throws PeriodNotAvailableException {
		List<Hotel> hotels = hotelService.findAll();
		assertTrue(hotels.size() > 0);
		Date startDate = DateUtils.addDays(new Date(), 2);
		Date endDate = DateUtils.addDays(new Date(), 5);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
		Hotel hotel = hotels.get(0);
		BigDecimal pricePerDay = new BigDecimal(100.99);
		Reservation newReservation = new Reservation(null, email, fullName, startDate, endDate, hotel, pricePerDay);
		Reservation savedReservation = reservationService.save(newReservation);
		assertTrue(savedReservation.getId() != null && savedReservation.getId() > 0);
	}

//	@Test
	public void testFindById() throws ReservationNotFoundException {
		Date startDate = DateUtils.addDays(new Date(), 9);
		Date endDate = DateUtils.addDays(new Date(), 10);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
		Long id = 1l;
		Reservation reservation = reservationService.findById(id);
//		Reservation reservation = reservationService.findById(id);
//		assertTrue(reservation.getId() > 0);
	}

//	@Test
	public void testCancelReservation() {
		Date startDate = DateUtils.addDays(new Date(), 11);
		Date endDate = DateUtils.addDays(new Date(), 12);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
//		Long id = reservationService.doReservation(startDate, endDate, email, fullName);
//		reservationService.cancelReservation(id);
	}

//	@Test
	public void testUpdateReservation() {
		Date startDate = DateUtils.addDays(new Date(), 13);
		Date endDate = DateUtils.addDays(new Date(), 14);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
		//
		Date newStartDate = DateUtils.addDays(new Date(), 15);
		Date newEndDate = DateUtils.addDays(new Date(), 16);
		String newEmail = "me@marcoreis.net";
		String newFullName = "marco";
//		Reservation reservation = reservationService.findById(id);
//		assertTrue("me@marcoreis.net".equals(reservation.getEmail()));
	}
}
