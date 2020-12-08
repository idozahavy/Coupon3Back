package com.idoz.coupons3.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.idoz.coupons3.beans.Company;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.rest.beans.ErrorMessage;
import com.idoz.coupons3.security.TokenManager;
import com.idoz.coupons3.service.AdminService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@RestController
@AllArgsConstructor
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	TokenManager tokenManager;

	@SneakyThrows
	@PostMapping("company")
	public ResponseEntity<?> addCompany(@RequestAttribute("service") AdminService service,
			@RequestBody Company company) {
		service.addCompany(company);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@SneakyThrows
	@DeleteMapping("company/{id}")
	public ResponseEntity<?> deleteCompany(@RequestAttribute("service") AdminService service, @PathVariable int id) {
		service.deleteCompany(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@SneakyThrows
	@PutMapping("company")
	public ResponseEntity<?> updateCompany(@RequestAttribute("service") AdminService service,
			@RequestBody Company company) {
		service.updateCompany(company);
		return new ResponseEntity<>("1", HttpStatus.ACCEPTED);
	}

	@SneakyThrows
	@GetMapping("company/{id}")
	public ResponseEntity<?> getOneCompany(@RequestAttribute("service") AdminService service, @PathVariable int id) {
		System.out.println("getOneCompany");
		return new ResponseEntity<>(service.getOneCompany(id), HttpStatus.OK);
	}

	@GetMapping("company/all")
	public ResponseEntity<?> getAllCompanies(@RequestAttribute("service") AdminService service) {
		return new ResponseEntity<>(service.getAllCompanies(), HttpStatus.OK);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SneakyThrows
	@PostMapping("customer")
	public ResponseEntity<?> addCustomer(@RequestAttribute("service") AdminService service,
			@RequestBody Customer customer) {
		service.addCustomer(customer);
		return new ResponseEntity<>("1", HttpStatus.CREATED);
	}

	@SneakyThrows
	@DeleteMapping("customer/{id}")
	public ResponseEntity<?> deleteCustomer(@RequestAttribute("service") AdminService service, @PathVariable int id) {
		service.deleteCustomer(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@SneakyThrows
	@PutMapping("customer")
	public ResponseEntity<?> updateCustomer(@RequestAttribute("service") AdminService service,
			@RequestBody Customer customer) {
		service.updateCustomer(customer);
		return new ResponseEntity<>(new ErrorMessage("1"), HttpStatus.ACCEPTED);
	}

	@SneakyThrows
	@GetMapping("customer/{id}")
	public ResponseEntity<?> getOneCustomer(@RequestAttribute("service") AdminService service, @PathVariable int id) {
		return new ResponseEntity<>(service.getOneCustomer(id), HttpStatus.OK);
	}

	@GetMapping("customer/all")
	public ResponseEntity<?> getAllCustomers(@RequestAttribute("service") AdminService service) {
		return new ResponseEntity<>(service.getAllCustomers(), HttpStatus.OK);
	}

}
