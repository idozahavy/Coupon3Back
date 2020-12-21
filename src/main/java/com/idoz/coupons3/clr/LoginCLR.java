package com.idoz.coupons3.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import com.idoz.coupons3.login.LoginManager;
import com.idoz.coupons3.service.AdminService;
import com.idoz.coupons3.service.CompanyService;
import com.idoz.coupons3.service.CustomerService;

//@Component
@Order(3)
public class LoginCLR implements CommandLineRunner {

	@Autowired
	private LoginManager loginManager;

	@Override
	public void run(String... args) throws Exception {

		Table200.noShowFields.clear();

		System.out.println();
		System.out.println(Art.stringToArtH1("- LoginManager -"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Admin Login "));
		AdminService adminService = loginManager.login("admin@admin.com", "admin", AdminService.class);
		System.out.println(Art.padTo120Stars(" Admin Login - getAllCompanies check "));
		Table200.print(adminService.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Admin Login (Fail - wrong credentials) "));
		adminService = loginManager.login("admin@admin.com", "8452", AdminService.class);
		System.out.println("Admin Service = " + adminService);

		System.out.println();
		System.out.println(Art.padTo120Stars(" Company Login "));
		CompanyService companyService = loginManager.login("myamd@cc.com", "2345", CompanyService.class);
		System.out.println(Art.padTo120Stars(" Company Login - getCompanyDetails check "));
		Table200.print(companyService.getCompanyDetails());

		System.out.println(Art.padTo120Stars(" Company Login (Fail - wrong credentials) "));
		companyService = loginManager.login("myamd@cc.com", "1234", CompanyService.class);
		System.out.println("Company Service = " + companyService);

		System.out.println();
		System.out.println(Art.padTo120Stars(" Customer Login "));
		CustomerService customerService = loginManager.login("mail1@a", "1234", CustomerService.class);
		System.out.println(Art.padTo120Stars(" Customer Login - getCustomerDetails check "));
		Table200.print(customerService.getCustomerDetails());

		System.out.println(Art.padTo120Stars(" Customer Login (Fail - wrong credentials) "));
		customerService = loginManager.login("mail1", "22", CustomerService.class);
		System.out.println("Customer Service = " + customerService);
	}

}
