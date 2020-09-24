package net.onlinereservation.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.onlinereservation.entity.Hotel;
import net.onlinereservation.repository.HotelRepository;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

	private HotelRepository hotelRepository;

	@Autowired
	public HotelServiceImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@PostConstruct
	public void init() {
		log.info("init: " + this.getClass());
	}

	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}

}
