package com.example.pincodedistance.service;

import com.example.pincodedistance.dto.DistanceResponse;

public interface IGoogleMaps {

	public DistanceResponse fetchDistance(String fromPincode, String toPincode);
}
