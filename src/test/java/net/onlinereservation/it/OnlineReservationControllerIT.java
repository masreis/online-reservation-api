package net.onlinereservation.it;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import net.onlinereservation.dto.HotelDTO;
import net.onlinereservation.dto.ReservationDTO;
import net.onlinereservation.dto.Response;

//@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureWebMvc
@Slf4j
public class OnlineReservationControllerIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private String localhost = "http://localhost:";

//	@Test
	@Order(1)
	public void testInsertSampleHotels() {
		for (int i = 0; i < 1000; i++) {
			String email = RandomStringUtils.random(8, true, false) + "@" + RandomStringUtils.random(8, true, false)
					+ ".com";
			String name = RandomStringUtils.random(10, true, false);
			String address = RandomStringUtils.random(10, true, false);
			String phoneNumber = RandomStringUtils.random(4, false, true) + "-"
					+ RandomStringUtils.random(4, false, true);
			Integer numberOfRooms = new Random().nextInt(50) + 1;

			HotelDTO hotel = new HotelDTO(null, email, name, address, phoneNumber, numberOfRooms);
			HttpEntity<HotelDTO> entity = new HttpEntity<HotelDTO>(hotel);
			ResponseEntity<String> response = this.testRestTemplate.exchange(localhost + port + "/v1/hotels",
					HttpMethod.POST, entity, String.class);
			assertTrue(201 == response.getStatusCodeValue());
		}
	}

	@Test
	@Order(2)
	public void testFindAllHotels() {
		ResponseEntity<Response<List<HotelDTO>>> response = testRestTemplate.exchange(localhost + port + "/v1/hotels",
				HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<HotelDTO>>>() {
				});
		List<HotelDTO> hotels = response.getBody().getData();
		assertTrue(hotels.size() > 0);
	}

	@Test
	@Order(2)
	public void testInsertSampleReservations() {

		Random random = new Random();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		ResponseEntity<Response<List<HotelDTO>>> responseHotel = testRestTemplate.exchange(
				localhost + port + "/v1/hotels", HttpMethod.GET, null,
				new ParameterizedTypeReference<Response<List<HotelDTO>>>() {
				});
		List<HotelDTO> hotels = responseHotel.getBody().getData();

		for (int i = 0; i < 100000; i++) {
			String email = RandomStringUtils.random(8, true, false) + "@" + RandomStringUtils.random(8, true, false)
					+ ".com";
			String fullName = RandomStringUtils.random(10, true, false);
			Date startDate = DateUtils.addDays(new Date(), random.nextInt(100) + 1);
			Date endDate = DateUtils.addDays(startDate, random.nextInt(30));
			HotelDTO hotel = hotels.get(random.nextInt(hotels.size()));

			// is availabe?
			String url = localhost + port + "/v1/reservations/available?startDate=" + sdf.format(startDate)
					+ "&endDate=" + sdf.format(endDate) + "&idHotel=" + hotel.getId();
			ResponseEntity<Response<Boolean>> responseAvailable = testRestTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<Response<Boolean>>() {
					});
			if (!responseAvailable.getBody().getData()) {
				log.info("Not available.");
				continue;
			}

			BigDecimal pricePerDay = BigDecimal.valueOf(random.nextInt(500));
			ReservationDTO reservation = new ReservationDTO(null, email, fullName, startDate, endDate, hotel,
					pricePerDay);
			HttpEntity<ReservationDTO> entity = new HttpEntity<ReservationDTO>(reservation);
			ResponseEntity<String> response = testRestTemplate.exchange(localhost + port + "/v1/reservations",
					HttpMethod.POST, entity, String.class);
			assertTrue(201 == response.getStatusCodeValue());
		}
	}

}
