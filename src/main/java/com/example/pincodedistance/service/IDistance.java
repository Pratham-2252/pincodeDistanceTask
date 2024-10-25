package com.example.pincodedistance.service;

import com.example.pincodedistance.dto.DistanceResponse;

public interface IDistance {

	public DistanceResponse getDistance(String fromPincode, String toPincode);
}
