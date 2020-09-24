package net.onlinereservation.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.onlinereservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query(value = "SELECT count(id) > 1 from Reservation where hotel.id = :idHotel and ("
			+ " (startdate >= :startDate and startdate <= :endDate) "
			+ "or (startDate BETWEEN :startDate and :endDate) or (enddate BETWEEN :startDate and :endDate) "
			+ ")", nativeQuery = true)
	boolean isScheduled(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("idHotel") Long idHotel);

	Page<Reservation> findAllByStartDateBetween(Date startDate, Date endDate, Pageable pg);

}
