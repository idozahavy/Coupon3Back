package com.idoz.coupons3.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.repo.CouponRepository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CouponController {

	private CouponRepository couponRepo;

	@SneakyThrows
	@GetMapping("coupons/{page}")
	public ResponseEntity<?> getAllCoupons(@PathVariable(required = false) Integer page, @RequestParam(defaultValue = "5") int count) {
		if (page == null || page < 1) {
			throw new DataManipulationException("did not pass correct page");
		}
		Page<Coupon> coupons = couponRepo.findAll(PageRequest.of(page - 1, count));
		return new ResponseEntity<>(coupons.getContent(), HttpStatus.OK);
	}
	
	
	@SneakyThrows
	@GetMapping("couponsCount")
	public ResponseEntity<?> getAllCoupons() {
		return new ResponseEntity<>(couponRepo.count(), HttpStatus.OK);
	}


}
