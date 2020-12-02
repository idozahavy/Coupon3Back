package com.idoz.coupons3.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.idoz.coupons3.beans.Company;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.exceptions.*;
import com.idoz.coupons3.rest.beans.ErrorMessage;
import com.idoz.coupons3.security.TokenManager;
import com.idoz.coupons3.service.AdminService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	TokenManager tokenManager;

	@PostMapping("company")
	public ResponseEntity<?> addCompany(@RequestAttribute("service") AdminService service,
			@RequestBody Company company) {
		try {
			service.addCompany(company);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (DetailDuplicationException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("company/{id}")
	public ResponseEntity<?> deleteCompany(@RequestAttribute("service") AdminService service, @PathVariable int id) {
		try {
			service.deleteCompany(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (WrongIdException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("company")
	public ResponseEntity<?> updateCompany(@RequestAttribute("service") AdminService service,
			@RequestBody Company company) {
		try {
			service.updateCompany(company);
			return new ResponseEntity<>("1", HttpStatus.ACCEPTED);
		} catch (WrongIdException | DataManipulationException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("company/{id}")
	public ResponseEntity<?> getOneCompany(@RequestAttribute("service") AdminService service, @PathVariable int id) {
		System.out.println("getOneCompany");
		try {
			return new ResponseEntity<>(service.getOneCompany(id), HttpStatus.OK);
		} catch (WrongIdException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("company/all")
	public ResponseEntity<?> getAllCompanies(@RequestAttribute("service") AdminService service) {
		return new ResponseEntity<>(service.getAllCompanies(), HttpStatus.OK);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping("customer")
	public ResponseEntity<?> addCustomer(@RequestAttribute("service") AdminService service,
			@RequestBody Customer customer) {
		try {
			service.addCustomer(customer);
			return new ResponseEntity<>("1", HttpStatus.CREATED);
		} catch (DetailDuplicationException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("customer/{id}")
	public ResponseEntity<?> deleteCustomer(@RequestAttribute("service") AdminService service,
			@PathVariable int id) {
		try {
			service.deleteCustomer(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (WrongIdException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("customer")
	public ResponseEntity<?> updateCustomer(@RequestAttribute("service") AdminService service,
			@RequestBody Customer customer) {
		try {
			service.updateCustomer(customer);
			return new ResponseEntity<>(new ErrorMessage("1"), HttpStatus.ACCEPTED);
		} catch (WrongIdException | DataManipulationException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("customer/{id}")
	public ResponseEntity<?> getOneCustomer(@RequestAttribute("service") AdminService service,
			@PathVariable int id) {
		try {
			return new ResponseEntity<>(service.getOneCustomer(id), HttpStatus.OK);
		} catch (WrongIdException e) {
			return new ResponseEntity<>(new ErrorMessage(e), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("customer/all")
	public ResponseEntity<?> getAllCustomers(@RequestAttribute("service") AdminService service) {
		return new ResponseEntity<>(service.getAllCustomers(), HttpStatus.OK);
	}

}
