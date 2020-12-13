package com.idoz.coupons3.clr.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.clr.Art;
import com.idoz.coupons3.clr.Table200;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;
import com.idoz.coupons3.repo.CouponRepository;
import com.idoz.coupons3.repo.CustomerRepository;
import com.idoz.coupons3.service.CustomerService;

@Component
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public void run() {
		System.out.println();
		System.out.println(Art.stringToArtH1("CustomerService Test"));

		System.out.println();

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Fail) "));
		System.out.println(customerService.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Success) "));
		System.out.println(customerService.login("mail1@a", "1234"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Details "));
		Table200.print(customerService.getCustomerDetails());

		System.out.println(Art.padTo120Stars(" Get Customer Coupons "));
		Table200.print(customerService.getCustomerCoupons());

		System.out.println(Art.padTo120Stars(" Get Customer Coupons Category Computers "));
		Table200.print(customerService.getCustomerCoupons(Category.Computers));

		System.out.println(Art.padTo120Stars(" Get Customer Coupons Max Price 40 "));
		Table200.print(customerService.getCustomerCoupons(40));
		
		List<Coupon> allCoupons = couponRepository.findAll();
		Coupon couponPurchased = allCoupons.get(allCoupons.size() - 1);

		System.out.println(Art.padTo120Stars(" Purchase Coupon "));
		System.out.println("Before Coupons - ");
		Table200.print(couponRepository.findAll());
		System.out.println("Before - ");
		Table200.print(customerService.getCustomerCoupons());
		try {
			customerService.purchaseCoupon(couponPurchased);
		} catch (WrongIdException | DataManipulationException | DetailDuplicationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(customerService.getCustomerCoupons());
		System.out.println(" After Coupons - ");
		Table200.print(couponRepository.findAll());

		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - already own this coupon) "));
		System.out.println("Before - ");
		Table200.print(customerService.getCustomerCoupons());
		try {
			customerService.purchaseCoupon(couponPurchased);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException | DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(customerService.getCustomerCoupons());
		
		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - coupon does not exist) "));
		try {
			Coupon noCoupon = new Coupon(0, 0, Category.AI, "", "", new Date(0), new Date(0), 0, 0, "");
			customerService.purchaseCoupon(noCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException | DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		
		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - outdated) "));
		try {
			Coupon outdatedCoupon = new Coupon(4, 0, Category.AI, "", "", new Date(0), new Date(0), 0, 0, "");
			customerService.purchaseCoupon(outdatedCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException | DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		
		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - amount 0) "));
		try {
			Coupon noAmountCoupon = new Coupon(5, 0, Category.AI, "", "", new Date(0), new Date(0), 0, 0, "");
			customerService.purchaseCoupon(noAmountCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException | DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		
		couponPurchased = couponRepository.getOne(couponPurchased.getId());
		Customer customer = customerService.getCustomerDetails();
		customer.removeCoupon(couponPurchased);
		customerRepository.saveAndFlush(customer);
		
	}

}
