package com.idoz.coupons3.clr;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Company;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.repo.CompanyRepository;
import com.idoz.coupons3.repo.CouponRepository;
import com.idoz.coupons3.repo.CustomerRepository;

@Component
@Order(1)
public class DatabaseInitCLR implements CommandLineRunner {

	@Autowired
	private CouponRepository couponRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private CompanyRepository companyRepo;

	@Override
	public void run(String... args) throws Exception {
		InitializeDB();
	}

	private void InitializeDB() {
		addAllCompanies();
		addAllCustomers();
		addAllCoupons();
		addAllPurchases();
	}

	public void addAllPurchases() {
		Coupon cp1 = couponRepo.getOne(1);
		Coupon cp2 = couponRepo.getOne(2);
		Coupon cp3 = couponRepo.getOne(3);
//		Coupon cp4 = couponRepo.getOne(4);

		Customer cust1 = customerRepo.getOne(1);
		cust1.purchaseCoupon(cp1);
		cust1.purchaseCoupon(cp2);
		cust1.purchaseCoupon(cp3);
		customerRepo.saveAndFlush(cust1);

		Customer cust2 = customerRepo.getOne(2);
		cust2.purchaseCoupon(cp1);
		cust2.purchaseCoupon(cp3);
		customerRepo.saveAndFlush(cust2);

		Customer cust3 = customerRepo.getOne(3);
		cust3.purchaseCoupon(cp2);
		customerRepo.saveAndFlush(cust3);

//		Customer cust4 = customerRepo.getOne(4);
//		cust4.purchaseCoupon(cp4);
//		customerRepo.saveAndFlush(cust4);

		cp1.setAmount(cp1.getAmount() - 2);
		cp2.setAmount(cp2.getAmount() - 2);
		cp3.setAmount(cp3.getAmount() - 2);
//		cp4.setAmount(cp4.getAmount() - 1);

		couponRepo.saveAll(List.of(cp1, cp2, cp3));
		couponRepo.flush();
	}

	public void addAllCompanies() {
		Company ibm = new Company("ibm", "mymail@cc.com", "1234");
		Company amd = new Company("amd", "myamd@cc.com", "2345");

		companyRepo.saveAndFlush(ibm);
		companyRepo.saveAndFlush(amd);
	}

	private static final String IMG_1 = "https://ecommerceguide.com/wp-content/uploads/2016/01/coupon-main.jpg";
	private static final String IMG_2 = "https://mk0einvestigatoid9ob.kinstacdn.com/wp-content/uploads/2013/01/discount-coupons-696x298.jpg";
	private static final String IMG_3 = "https://support.unicart.com/wp-content/uploads/2013/11/coupon-code-1.jpg";

	public void addAllCoupons() {
		Coupon coupon1 = new Coupon(0, 1, Category.Electricity, "first coupon", "first", Date.valueOf("2020-08-08"),
				Date.valueOf("2021-12-16"), 50, 10.90, IMG_1);
		Coupon coupon2 = new Coupon(0, 1, Category.Computers, "second coupon", "gro", Date.valueOf("2020-09-08"),
				Date.valueOf("2021-12-18"), 40, 50.90, IMG_3);
		Coupon coupon3 = new Coupon(0, 2, Category.AI, "third coupon", "shrik", Date.valueOf("2020-10-08"),
				Date.valueOf("2021-12-10"), 30, 69.90, IMG_2);
		Coupon coupon4 = new Coupon(0, 2, Category.Food, "outdated coupon", "out", Date.valueOf("2020-04-08"),
				Date.valueOf("2020-08-11"), 20, 12.50, IMG_3);
		Coupon coupon5 = new Coupon(0, 1, Category.Computers, "noAmountCoupon", "0amount", Date.valueOf("2020-12-03"),
				Date.valueOf("2021-12-11"), 0, 90.90, IMG_2);
		Coupon coupon6 = new Coupon(0, 2, Category.Computers, "sixth coupon", "desc4", Date.valueOf("2020-11-08"),
				Date.valueOf("2021-12-11"), 20, 49.90, IMG_1);

		couponRepo.save(coupon1);
		couponRepo.save(coupon2);
		couponRepo.save(coupon3);
		couponRepo.save(coupon4);
		couponRepo.save(coupon5);
		couponRepo.save(coupon6);
	}

	private void addAllCustomers() {
		Customer customer1 = new Customer("person1", "last1", "mail1@a", "1234");
		Customer customer2 = new Customer("person2", "last2", "mail2@b", "2222");
		Customer customer3 = new Customer("person3", "last3", "mail3", "3");
		Customer customer4 = new Customer("person4", "last4", "mail4", "4");

		customerRepo.saveAndFlush(customer1);
		customerRepo.saveAndFlush(customer2);
		customerRepo.saveAndFlush(customer3);
		customerRepo.saveAndFlush(customer4);
	}

}
