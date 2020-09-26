package net.onlinereservation.service;

import java.util.List;

import net.onlinereservation.entity.Hotel;
import net.onlinereservation.exception.HotelNotFoundException;

public interface HotelService {
	Hotel save(Hotel hotel);

	List<Hotel> findAll();

	Hotel findById(Long id) throws HotelNotFoundException;

}
