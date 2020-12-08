package com.idoz.coupons3.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.service.CustomerService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@RestController
@AllArgsConstructor
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@SneakyThrows
	@PostMapping("coupon")
	public ResponseEntity<?> purchaseCoupon(@RequestAttribute("service") CustomerService service, @RequestBody Coupon coupon){
		service.purchaseCoupon(coupon);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SneakyThrows
	@GetMapping
	public ResponseEntity<?> getDetails(@RequestAttribute("service") CustomerService service){
		return new ResponseEntity<>(service.getCustomerDetails(), HttpStatus.OK);
	}
	
}
