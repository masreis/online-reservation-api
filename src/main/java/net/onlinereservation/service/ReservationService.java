package net.onlinereservation.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.onlinereservation.entity.Reservation;
import net.onlinereservation.exception.OnlineReservationException;

public interface ReservationService {

	Reservation save(Reservation reservation) throws PeriodNotAvailableException;

	boolean isAvailable(Date startDate, Date endDate, Long idHotel);

	Reservation findById(Long id) throws OnlineReservationException;

	boolean existsById(Long id);

	Page<Reservation> findAllByDate(Date startDate, Date endDate, Pageable page);

	void deleteById(Long id);

}
