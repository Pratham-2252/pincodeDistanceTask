package com.example.pincodedistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pincodedistance.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

	public Optional<Route> findByFromPincodeAndToPincode(String fromPincode, String toPincode);
}
