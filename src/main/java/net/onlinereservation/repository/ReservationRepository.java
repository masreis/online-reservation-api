package net.onlinereservation.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.onlinereservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "select count(id) from reservation r where hotel_id = :idHotel and ("
            + " (start_date >= :startDate and start_date <= :endDate) "
            + "or (start_date BETWEEN :startDate and :endDate) or (end_date BETWEEN :startDate and :endDate) "
            + ")", nativeQuery = true)
    // TODO Validate number of rooms
    int roomsOccupied(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
            @Param("idHotel") Long idHotel);

    Page<Reservation> findAllByStartDateBetween(Date startDate, Date endDate, Pageable pg);

}
