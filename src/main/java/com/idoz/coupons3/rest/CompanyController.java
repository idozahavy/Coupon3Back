package com.idoz.coupons3.rest;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.idoz.coupons3.beans.*;
import com.idoz.coupons3.exceptions.*;
import com.idoz.coupons3.security.TokenManager;
import com.idoz.coupons3.service.CompanyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	TokenManager tokenManager;

	@PostMapping("coupon")
	public ResponseEntity<?> addCoupon(@RequestAttribute(name = "service") CompanyService service,
			@RequestBody Coupon coupon) {
		try {
			service.addCoupon(coupon);
			return new ResponseEntity<>("1", HttpStatus.CREATED);
		} catch (DetailDuplicationException | WrongIdException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("coupon")
	public ResponseEntity<?> updateCoupon(@RequestAttribute(name = "service") CompanyService service,
			@RequestBody Coupon coupon) {
		try {
			service.updateCoupon(coupon);
			return new ResponseEntity<>("1", HttpStatus.ACCEPTED);
		} catch (WrongIdException | DataManipulationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("coupon/{id}")
	public ResponseEntity<?> deleteCoupon(@RequestAttribute(name = "service") CompanyService service,
			@PathVariable int id) {
		try {
			service.deleteCoupon(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (WrongIdException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("coupons")
	public ResponseEntity<?> getCoupons(@RequestAttribute(name = "service") CompanyService service,
			@RequestParam(required = false) Category category, @RequestParam(required = false) Integer maxPrice) {
		if (category != null) {
			return new ResponseEntity<>(service.getCompanyCoupons(category), HttpStatus.OK);
		}
		if (maxPrice != null) {
			return new ResponseEntity<>(service.getCompanyCoupons(maxPrice), HttpStatus.OK);
		}
		return new ResponseEntity<>(service.getCompanyCoupons(), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> getDetails(@RequestAttribute(name = "service") CompanyService service) {
		return new ResponseEntity<>(service.getCompanyDetails(), HttpStatus.OK);
	}

}
