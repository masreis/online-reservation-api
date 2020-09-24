package net.onlinereservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.onlinereservation.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
