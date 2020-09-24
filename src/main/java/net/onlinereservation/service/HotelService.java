package net.onlinereservation.service;

import java.util.List;

import net.onlinereservation.entity.Hotel;

public interface HotelService {
	Hotel save(Hotel hotel);

	List<Hotel> findAll();
}
