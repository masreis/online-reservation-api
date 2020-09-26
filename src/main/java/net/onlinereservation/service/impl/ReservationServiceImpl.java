package net.onlinereservation.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.onlinereservation.entity.Hotel;
import net.onlinereservation.entity.Reservation;
import net.onlinereservation.exception.PeriodNotAvailableException;
import net.onlinereservation.exception.ReservationNotFoundException;
import net.onlinereservation.repository.ReservationRepository;
import net.onlinereservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private ReservationRepository reservationRepository;

	@Autowired
	public ReservationServiceImpl(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public Reservation save(Reservation reservation) throws PeriodNotAvailableException {
		return reservationRepository.save(reservation);
	}

	public boolean isAvailable(Date startDate, Date endDate, Hotel hotel) {
		int roomsOccupied = reservationRepository.roomsOccupied(startDate, endDate, hotel.getId());
		return roomsOccupied < hotel.getNumberOfRooms();
	}

	@Cacheable(value = "reservationByIdCache", key = "#id", unless = "#result==null")
	public Reservation findById(Long id) throws ReservationNotFoundException {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new ReservationNotFoundException("The reservation does not exist."));
	}

	public Page<Reservation> findAllByDate(Date startDate, Date endDate, Pageable pg) {

		return reservationRepository.findAllByStartDateBetween(startDate, endDate, pg);

	}

	@Override
	public boolean existsById(Long id) {
		return reservationRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		reservationRepository.deleteById(id);
	}

	@Override
	public Page<Reservation> findAll(Pageable pg) {
		return reservationRepository.findAll(pg);
	}

}
