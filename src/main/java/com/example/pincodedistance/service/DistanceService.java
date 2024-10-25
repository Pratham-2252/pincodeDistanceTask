package com.example.pincodedistance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pincodedistance.dto.DistanceResponse;
import com.example.pincodedistance.entity.Route;
import com.example.pincodedistance.repository.RouteRepository;

@Service
public class DistanceService implements IDistance {

	@Autowired
	private GoogleMapsService googleMapsService;

	@Autowired
	private RouteRepository routeRepository;

	@Override
	@Cacheable(value = "distanceCache", key = "#fromPincode + '-' + #toPincode")
	public DistanceResponse getDistance(String fromPincode, String toPincode) {

		return routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode).map(route -> {

			DistanceResponse distanceResponse = new DistanceResponse();

			distanceResponse.setDistance(route.getDistance().toString());
			distanceResponse.setDuration(route.getDuration());

			return distanceResponse;
		}).orElseGet(() -> {

			DistanceResponse distanceResponse = googleMapsService.fetchDistance(fromPincode, toPincode);

			saveRoute(fromPincode, toPincode, distanceResponse);

			return distanceResponse;
		});

	}

	private void saveRoute(String fromPincode, String toPincode, DistanceResponse distanceResponse) {
		Route route = new Route();

		String distanceStr = distanceResponse.getDistance();
		String numericDistance = distanceStr.replaceAll("[^0-9.]", "");

		route.setDistance(Double.valueOf(numericDistance));
		route.setDuration(distanceResponse.getDuration());
		route.setFromPincode(fromPincode);
		route.setToPincode(toPincode);

		routeRepository.save(route);
	}
}
