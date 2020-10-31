package net.onlinereservation.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.onlinereservation.entity.Hotel;
import net.onlinereservation.entity.Reservation;
import net.onlinereservation.exception.PeriodNotAvailableException;
import net.onlinereservation.exception.ReservationNotFoundException;

public interface ReservationService {

    Reservation save(Reservation reservation) throws PeriodNotAvailableException;

    boolean isAvailable(Date startDate, Date endDate, Hotel hotel);

    Reservation findById(Long id) throws ReservationNotFoundException;

    boolean existsById(Long id);

    Page<Reservation> findAllByDate(Date startDate, Date endDate, Pageable page);

    void deleteById(Long id);

    Page<Reservation> findAll(Pageable pg);

}
