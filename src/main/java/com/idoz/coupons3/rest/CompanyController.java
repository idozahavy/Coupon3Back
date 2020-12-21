package com.idoz.coupons3.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.rest.beans.ErrorMessage;
import com.idoz.coupons3.security.TokenManager;
import com.idoz.coupons3.service.CompanyService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@RestController
@AllArgsConstructor
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	TokenManager tokenManager;

	@SneakyThrows
	@PostMapping("coupon")
	public ResponseEntity<?> addCoupon(@RequestAttribute(name = "service") CompanyService service,
			@RequestBody Coupon coupon) {
		service.addCoupon(coupon);
		return new ResponseEntity<>(new ErrorMessage("1"), HttpStatus.CREATED);
	}

	@SneakyThrows
	@PutMapping("coupon")
	public ResponseEntity<?> updateCoupon(@RequestAttribute(name = "service") CompanyService service,
			@RequestBody Coupon coupon) {
		service.updateCoupon(coupon);
		return new ResponseEntity<>("1", HttpStatus.ACCEPTED);
	}

	@SneakyThrows
	@DeleteMapping("coupon/{id}")
	public ResponseEntity<?> deleteCoupon(@RequestAttribute(name = "service") CompanyService service,
			@PathVariable int id) {
		service.deleteCoupon(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("coupons")
	public ResponseEntity<?> getCoupons(@RequestAttribute(name = "service") CompanyService service,
			@RequestParam(required = false) Integer category, @RequestParam(required = false) Integer maxPrice) {
		if (category != null) {
			System.out.println("category = " + category);
			return new ResponseEntity<>(service.getCompanyCoupons(Category.values()[category]), HttpStatus.OK);
		}
		if (maxPrice != null) {
			System.out.println("maxPrice = " + maxPrice);
			return new ResponseEntity<>(service.getCompanyCoupons(maxPrice), HttpStatus.OK);
		}
		return new ResponseEntity<>(service.getCompanyCoupons(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getDetails(@RequestAttribute(name = "service") CompanyService service) {
		return new ResponseEntity<>(service.getCompanyDetails(), HttpStatus.OK);
	}

}
