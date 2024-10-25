package com.example.pincodedistance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.pincodedistance.dto.DistanceResponse;

@SpringBootTest
public class DistanceServiceTest {

	@Mock
	private GoogleMapsService googleMapsService;

	@InjectMocks
	private DistanceService distanceService;

	@Test
	public void testGetDistance() {
		DistanceResponse mockResponse = new DistanceResponse("10 km", "15 mins");

		when(googleMapsService.fetchDistance("141106", "110060")).thenReturn(mockResponse);

		DistanceResponse response = distanceService.getDistance("141106", "110060");

		assertEquals("10 km", response.getDistance());
		assertEquals("15 mins", response.getDuration());
	}
}
