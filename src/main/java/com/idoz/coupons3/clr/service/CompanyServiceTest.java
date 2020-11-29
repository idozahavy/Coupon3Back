package com.idoz.coupons3.clr.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.clr.Art;
import com.idoz.coupons3.clr.Table200;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;
import com.idoz.coupons3.repo.CouponRepository;
import com.idoz.coupons3.service.CompanyService;

@Component
public class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CouponRepository couponRepo;

	public void run() {
		System.out.println();
		System.out.println(Art.stringToArtH1("CompanyService Test"));

		System.out.println();

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Fail - wrong credentials) "));
		System.out.println(companyService.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Success) "));
		System.out.println(companyService.login("mymail@cc.com", "1234"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Details "));
		Table200.print(companyService.getCompanyDetails());

		System.out.println(Art.padTo120Stars(" Get Company Coupons "));
		Table200.print(companyService.getCompanyCoupons());

		System.out.println(Art.padTo120Stars(" Get Company Coupons Category Computers "));
		Table200.print(companyService.getCompanyCoupons(Category.Computers));

		System.out.println(Art.padTo120Stars(" Get Company Coupons Max Price 40 "));
		Table200.print(companyService.getCompanyCoupons(40));

		System.out.println(Art.padTo120Stars(" Add Coupon "));
		System.out.println("Before - ");
		Table200.print(companyService.getCompanyCoupons());
		try {
			companyService.addCoupon(
					new Coupon(0, companyService.getCompanyDetails().getId(), Category.Technology, "couptitle",
							"desc434", Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
		} catch (WrongIdException | DetailDuplicationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		List<Coupon> companyCoupons = companyService.getCompanyCoupons();
		Table200.print(companyCoupons);
		Coupon tempCoupon = (Coupon) companyCoupons.toArray()[companyCoupons.size() - 1];

		System.out.println(Art.padTo120Stars(" Add Coupon (Fail - coupon title already exist) "));
		try {
			companyService.addCoupon(
					new Coupon(0, companyService.getCompanyDetails().getId(), Category.Technology, "couptitle",
							"desc434", Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(companyService.getCompanyCoupons());

		System.out.println(Art.padTo120Stars(" Update Coupon [category] "));
		System.out.println("Before - ");
		Table200.print(couponRepo.getOne(tempCoupon.getId()));
		tempCoupon.setCategory(Category.Restaurant);
		try {
			companyService.updateCoupon(tempCoupon);
		} catch (WrongIdException | DataManipulationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(couponRepo.getOne(tempCoupon.getId()));

		System.out
				.println(Art.padTo120Stars(" Update Coupon [category, companyId] (Fail - can not change companyId) "));
		tempCoupon.setCategory(Category.Vacation);
		tempCoupon.setCompanyId(-1);
		try {
			companyService.updateCoupon(tempCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(couponRepo.getOne(tempCoupon.getId()));
		tempCoupon.setCompanyId(companyService.getCompanyDetails().getId());

		System.out.println(Art.padTo120Stars(" Update Coupon -1 (Fail - coupon id does not exist) "));
		System.out.println("Before - ");
		Table200.print(companyService.getCompanyCoupons());
		tempCoupon.setCategory(Category.Vacation);
		int tempCouponId = tempCoupon.getId();
		tempCoupon.setId(-1);
		try {
			companyService.updateCoupon(tempCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(companyService.getCompanyCoupons());
		tempCoupon.setId(tempCouponId);

		System.out.println(Art.padTo120Stars(" Update Coupon 3 (Fail - coupon does not belong to company) "));
		tempCoupon.setCategory(Category.Vacation);
		tempCoupon.setId(3);
		try {
			companyService.updateCoupon(tempCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		tempCoupon.setId(tempCouponId);

		tempCoupon.setId(3);
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon 3 (Fail - coupon doesn't belong to company) "));
		System.out.println("Before - ");
		Table200.print(couponRepo.findAll());
		try {
			companyService.deleteCoupon(tempCoupon.getId());
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(couponRepo.findAll());
		tempCoupon.setId(tempCouponId);

		System.out.println(Art.padTo120Stars(" Delete Coupon (Fail, wrong id) "));
		try {
			companyService.deleteCoupon(-1);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(couponRepo.findAll());

		System.out.println(Art.padTo120Stars(" Delete Coupon "));
		try {
			companyService.deleteCoupon(tempCoupon.getId());
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table200.print(couponRepo.findAll());
	}

}
