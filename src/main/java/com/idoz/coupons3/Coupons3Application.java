package com.idoz.coupons3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Coupons3Application {

	public static void main(String[] args) {
		SpringApplication.run(Coupons3Application.class, args);
		System.out.println("Inversion of control initialized");
	}

}
