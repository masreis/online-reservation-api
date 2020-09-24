package net.marcoreis.onlinereservation.service;

import java.util.Currency;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.onlinereservation.entity.Hotel;
import net.onlinereservation.entity.Reservation;
import net.onlinereservation.service.PeriodNotAvailableException;
import net.onlinereservation.service.ReservationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
	@Autowired
	private ReservationServiceImpl reservationService;

	@Test
	public void testDoReservation() throws PeriodNotAvailableException {
		Date startDate = DateUtils.addDays(new Date(), 2);
		Date endDate = DateUtils.addDays(new Date(), 5);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
		Hotel hotel = null;
		Currency pricePerDay = null;
		Reservation reservation = new Reservation(null, email, fullName, startDate, endDate, hotel, pricePerDay);
		Reservation idReservation = reservationService.save(reservation);
//		assertTrue(idReservation != null && idReservation > 0);
	}

	@Test
	public void testUnavailablePeriod() {
		Date startDate = DateUtils.addDays(new Date(), 6);
		Date endDate = DateUtils.addDays(new Date(), 8);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
//		reservationService.doReservation(startDate, endDate, email, fullName);
		//
		startDate = DateUtils.addDays(new Date(), 7);
		endDate = DateUtils.addDays(new Date(), 9);
//		boolean available = reservationService.isAvailable(startDate, endDate);
//		assertTrue(!available);
	}

	@Test
	public void testFindById() {
		Date startDate = DateUtils.addDays(new Date(), 9);
		Date endDate = DateUtils.addDays(new Date(), 10);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
//		Long id = reservationService.doReservation(startDate, endDate, email, fullName);
//		Reservation reservation = reservationService.findById(id);
//		assertTrue(reservation.getId() > 0);
	}

	@Test
	public void testCancelReservation() {
		Date startDate = DateUtils.addDays(new Date(), 11);
		Date endDate = DateUtils.addDays(new Date(), 12);
		String email = "ma@marcoreis.net";
		String fullName = "marco";
//		Long id = reservationService.doReservation(startDate, endDate, email, fullName);
//		reservationService.cancelReservation(id);
	}

	@Test
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
