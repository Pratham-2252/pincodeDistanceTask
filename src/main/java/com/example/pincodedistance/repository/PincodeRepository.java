package com.example.pincodedistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pincodedistance.entity.Pincode;

@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long> {

}
