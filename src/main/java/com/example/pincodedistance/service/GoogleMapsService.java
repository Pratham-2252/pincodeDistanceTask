package com.example.pincodedistance.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pincodedistance.dto.DistanceResponse;

@Service
public class GoogleMapsService implements IGoogleMaps {

	@Value("${google.maps.api.key}")
	private String apiKey;

	private static final String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins={fromPincode}&destinations={toPincode}&key={apiKey}";

	@Override
	public DistanceResponse fetchDistance(String fromPincode, String toPincode) {

		RestTemplate restTemplate = new RestTemplate();

		String url = DISTANCE_MATRIX_URL.replace("{fromPincode}", fromPincode).replace("{toPincode}", toPincode)
				.replace("{apiKey}", apiKey);

		ResponseEntity<DistanceResponse> response = restTemplate.getForEntity(url, DistanceResponse.class);

		return response.getBody();
	}

}
