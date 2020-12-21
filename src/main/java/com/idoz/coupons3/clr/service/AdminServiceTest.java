package com.idoz.coupons3.clr.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Company;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.clr.Art;
import com.idoz.coupons3.clr.Table200;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;
import com.idoz.coupons3.repo.CompanyRepository;
import com.idoz.coupons3.repo.CouponRepository;
import com.idoz.coupons3.repo.CustomerRepository;
import com.idoz.coupons3.service.AdminService;

@Component
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CouponRepository couponRepo;

	@Autowired
	private CompanyRepository companyRepo;

	public void run() {
		System.out.println();
		System.out.println(Art.stringToArtH1("AdminService Test"));

		System.out.println();

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Fail - wrong credentials) "));
		System.out.println(adminService.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Success) "));
		System.out.println(adminService.login("admin@admin.com", "admin"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get All Companies "));
		Table200.print(adminService.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Get All Customers "));
		Table200.print(adminService.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Add Company "));
		try {
			adminService.addCompany(new Company(0, "adminCompany", "bjdfjk", "hashcodedpass", null));
		} catch (DetailDuplicationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		List<Company> allCompanies = adminService.getAllCompanies();
		Table200.print(allCompanies);
		Company tempCompany = allCompanies.get(allCompanies.size() - 1);

		System.out.println(Art.padTo120Stars(" Add Company (Fail - company name exists) "));
		try {
			adminService.addCompany(new Company(0, "adminCompany", "bjdfjk", "hashcodedpass", null));
			System.err.println("failed error------------------------------");
		} catch (DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Get One Company "));
		try {
			Table200.print(adminService.getOneCompany(tempCompany.getId()));
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(Art.padTo120Stars(" Update Company [email] "));
		tempCompany.setEmail("company_new2_Email");
		try {
			adminService.updateCompany(tempCompany);
		} catch (DataManipulationException | WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		try {
			Table200.print(adminService.getOneCompany(tempCompany.getId()));
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(Art.padTo120Stars(" Update Company [email, name] (Fail - cannot change name) "));
		tempCompany.setEmail("sabba.com_Email");
		tempCompany.setName("sucks to be me");
		try {
			adminService.updateCompany(tempCompany);
			System.err.println("failed error------------------------------");
		} catch (DataManipulationException | WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		try {
			Table200.print(adminService.getOneCompany(tempCompany.getId()));
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(Art.padTo120Stars(" added 1 coupon to tempCompany for testings "));
		tempCompany.addCoupon(new Coupon(0, tempCompany.getId(), Category.AI, "DeepMind", "wow",
				Date.valueOf("2017-08-10"), Date.valueOf("2025-08-10"), 1, 1 << 10, "aif"));
		companyRepo.saveAndFlush(tempCompany);
		tempCompany = companyRepo.getOne(tempCompany.getId());
		System.out.println();

		System.out.println(Art.padTo120Stars(" Delete Company (Fail - company does not exist) "));
		try {
			adminService.deleteCompany(-1);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Delete Company "));
		try {
			adminService.deleteCompany(tempCompany.getId());
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Add Customer "));
		System.out.println("Before - ");
		Table200.print(adminService.getAllCustomers());
		try {
			adminService.addCustomer(new Customer(0, "firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare", null));
		} catch (DetailDuplicationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		List<Customer> allCustomers = adminService.getAllCustomers();
		Table200.print(allCustomers);
		Customer tempCustomer = allCustomers.get(allCustomers.size() - 1);

		System.out.println(Art.padTo120Stars(" Add Customer (Fail - existing email) "));
		try {
			adminService.addCustomer(new Customer(0, "firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare", null));
			System.err.println("failed error------------------------------");
		} catch (DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Get One Customer "));
		try {
			Table200.print(adminService.getOneCustomer(tempCustomer.getId()));
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(Art.padTo120Stars(" Get One Customer (Fail - customer id not exist) "));
		try {
			Table200.print(adminService.getOneCustomer(-1));
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Throw - " + e.getMessage());
		}

		System.out.println(Art.padTo120Stars(" Update Customer (Fail - customer does not exist) "));
		try {
			adminService.updateCustomer(new Customer(-1, "aaa", "eee", "mail", "pass", null));
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Update Customer [email, firstname] "));
		System.out.println("Before - ");
		try {
			Table200.print(adminService.getOneCustomer(tempCustomer.getId()));
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		tempCustomer.setEmail("company_new2_Email");
		tempCustomer.setFirstName("cust1name504");
		try {
			adminService.updateCustomer(tempCustomer);
		} catch (WrongIdException | DataManipulationException e) {
			System.err.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		try {
			Table200.print(adminService.getOneCustomer(tempCustomer.getId()));
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}

		System.out.println(Art.padTo120Stars(" added 1 coupon purchase to tempCustomer for testings "));
		try {
			tempCustomer = adminService.getOneCustomer(tempCustomer.getId());
			tempCustomer.purchaseCoupon(couponRepo.getOne(1));
			customerRepo.saveAndFlush(tempCustomer);
		} catch (WrongIdException e) {
			System.err.println(e);
		}
		System.out.println();

		System.out.println(Art.padTo120Stars(" Delete Customer (Fail , no such id) "));
		try {
			adminService.deleteCustomer(-1);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Delete Customer "));
		try {
			adminService.deleteCustomer(tempCustomer.getId());
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(adminService.getAllCustomers());

	}

}
