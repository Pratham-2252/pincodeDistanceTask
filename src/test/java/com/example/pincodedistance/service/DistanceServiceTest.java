package com.example.pincodedistance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.pincodedistance.dto.DistanceResponse;
import com.example.pincodedistance.entity.Route;
import com.example.pincodedistance.repository.RouteRepository;

@ExtendWith(MockitoExtension.class)
class DistanceServiceTest {

	@Mock
	private GoogleMapsService googleMapsService;

	@Mock
	private RouteRepository routeRepository;

	@InjectMocks
	private DistanceService distanceService;

	private DistanceResponse mockApiDistanceResponse;

	private Route mockRoute;

	@BeforeEach
	void setUp() {
		mockApiDistanceResponse = new DistanceResponse("10 km", "15 mins");
		mockRoute = new Route();
		mockRoute.setFromPincode("141106");
		mockRoute.setToPincode("110060");
		mockRoute.setDistance(10.0);
		mockRoute.setDuration("15 mins");
	}

	@Test
	void testGetDistance_CacheHit() {

		when(routeRepository.findByFromPincodeAndToPincode("141106", "110060")).thenReturn(Optional.of(mockRoute));

		DistanceResponse response = distanceService.getDistance("141106", "110060");

		assertEquals("10.0", response.getDistance());
		assertEquals("15 mins", response.getDuration());
		verify(routeRepository, times(1)).findByFromPincodeAndToPincode("141106", "110060");
		verifyNoInteractions(googleMapsService);
	}

	@Test
	void testGetDistance_ApiCallWhenNoCache() {

		when(routeRepository.findByFromPincodeAndToPincode("141106", "110060")).thenReturn(Optional.empty());
		when(googleMapsService.fetchDistance("141106", "110060")).thenReturn(mockApiDistanceResponse);

		DistanceResponse response = distanceService.getDistance("141106", "110060");

		assertEquals("10 km", response.getDistance());
		assertEquals("15 mins", response.getDuration());
		verify(googleMapsService, times(1)).fetchDistance("141106", "110060");
		verify(routeRepository, times(1)).save(any(Route.class));
	}

	@Test
	void testSaveRoute_Success() {

		when(routeRepository.findByFromPincodeAndToPincode(anyString(), anyString())).thenReturn(Optional.empty());
		when(googleMapsService.fetchDistance("141106", "110060")).thenReturn(mockApiDistanceResponse);

		distanceService.getDistance("141106", "110060");

		verify(routeRepository, times(1)).save(any(Route.class));
	}
}
