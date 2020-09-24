package net.onlinereservation.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.onlinereservation.entity.Reservation;
import net.onlinereservation.exception.OnlineReservationException;
import net.onlinereservation.repository.ReservationRepository;

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

	public boolean isAvailable(Date startDate, Date endDate, Long idHotel) {
		return !reservationRepository.isScheduled(startDate, endDate, idHotel);
	}

	public Reservation findById(Long id) throws OnlineReservationException {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new OnlineReservationException("The reservation does not exist."));
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

}
