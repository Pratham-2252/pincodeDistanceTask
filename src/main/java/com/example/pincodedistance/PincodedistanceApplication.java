package com.example.pincodedistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PincodedistanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PincodedistanceApplication.class, args);
	}

}
