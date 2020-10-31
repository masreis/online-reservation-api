package net.onlinereservation.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.onlinereservation.entity.Hotel;
import net.onlinereservation.exception.HotelNotFoundException;
import net.onlinereservation.repository.HotelRepository;
import net.onlinereservation.service.HotelService;

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

    @Cacheable(value = "hotelByIdCache", key = "#id")
    public Hotel findById(Long id) throws HotelNotFoundException {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found."));
    }

}
