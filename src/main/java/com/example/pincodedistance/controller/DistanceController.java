package com.example.pincodedistance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pincodedistance.dto.DistanceResponse;
import com.example.pincodedistance.service.IDistance;

@RestController
@RequestMapping("/api/v1")
public class DistanceController {

	@Autowired
	private IDistance distanceService;

	@GetMapping("/distance")
	public ResponseEntity<DistanceResponse> getDistance(@RequestParam String fromPincode,
			@RequestParam String toPincode) {

		DistanceResponse response = distanceService.getDistance(fromPincode, toPincode);

		return ResponseEntity.ok(response);
	}
}
