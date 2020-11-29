package com.idoz.coupons3.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.clr.service.AdminServiceTest;
import com.idoz.coupons3.clr.service.CompanyServiceTest;
import com.idoz.coupons3.clr.service.CustomerServiceTest;

//@Component
@Order(2)
public class ServicesCLR implements CommandLineRunner {

	@Autowired
	private CustomerServiceTest customerTest;

	@Autowired
	private CompanyServiceTest companyTest;

	@Autowired
	private AdminServiceTest adminTest;

	@Override
	public void run(String... args) {
		Table200.noShowFields.clear();
		Table200.noShowFields.add("coupons");
		
		customerTest.run();
		companyTest.run();
		adminTest.run();
	}

	@SuppressWarnings("unused")
	private void old() {
//		Company com1 = new Company();
//		com1.setEmail("email2");
//		com1.setName("myName");
//		com1.setPassword("password");
//		companyRepository.save(com1);
//
//		Coupon coup1 = new Coupon();
//		coup1.setTitle("first title");
//		coup1.setCategory(Category.Computers);
//		coup1.setAmount(5);
//		coup1.setEndDate(Date.valueOf("2021-08-05"));
////		coup1.setCompany(com1);
//
//		Coupon coup2 = new Coupon();
//		coup2.setTitle("first 2title");
//		coup2.setCategory(Category.AI);
//		coup2.setAmount(51);
//		coup2.setEndDate(Date.valueOf("2020-08-05"));
//
////		couponRepository.saveAndFlush(coup1);
////		couponRepository.saveAndFlush(coup2);
//		com1.addCoupon(coup1);
//		com1.addCoupon(coup2);
//		companyRepository.saveAndFlush(com1);
//		coup1 = couponRepository.getOne(1);
//
////		couponRepository.delete(coup1);
////		companyRepository.delete(com1);
//
//		Customer cust1 = new Customer(0, "ido", "zahavy", "mymymy", "nonono", null);
//		Customer cust2 = new Customer(0, "ido", "zahavy", "mymymy2", "nonono2", null);
//		customerRepository.saveAndFlush(cust1);
//		customerRepository.saveAndFlush(cust2);
//
//		customerService.login(cust1.getEmail(), cust1.getPassword());
//		try {
//			customerService.purchaseCoupon(coup1);
//		} catch (DataManipulationException | WrongIdException | DetailDuplicationException e) {
//			System.out.println(e.getMessage());
//			System.out.println(couponRepository.getOne(coup1.getId()));
//		}
//		System.out.println(customerService.getCustomerDetails().getCoupons().size());
////		cust1 = customerRepository.getOne(1);
////		Pretty.printlnPrivates(cust1.getPurchases());
////		Table100.print(cust1);
////		Pretty.printlnPrivates(customerService.getCustomerCoupons());
//
//		System.out.println("------------------------------------");
//		final Coupon coupon1 = coup1;
//		customerRepository.findByCouponsContaining(coup1).stream().forEach((Customer cust) -> {
//			cust.removeCoupon(coupon1);
//			customerRepository.saveAndFlush(cust);
//		});
//		couponRepository.delete(coup1);

//		companyRepository.delete(com1);

//		companyService.login(com1.getEmail(), com1.getPassword());
//		companyService.deleteCoupon(coup1.getId());

//		System.out.println(customerService.getCustomerCoupons().size());

//		customerRepository.delete(cust1);
//		System.out.println(customerRepository.getOne(2).getEmail());

//		Pretty.println(companyService.getAll(), new PrettyConvertorConfig(PrettyAccessor.Private, false, false));
//		System.out.println();
//
//		System.out.println(companyService.login(com1.getEmail(), com1.getPassword()));
//		
//		System.out.println();
//		Coupon coup1 = new Coupon(0, companyRepository.getOne(1), Category.Computers, "tt", "desc", Date.valueOf("2020-12-03"),
//				Date.valueOf("2021-12-03"), 2, 99.9, "no image");
//		com1.setCoupons(List.of(coup1));
//		couponRepository.saveAndFlush(coup1);
//		companyRepository.saveAndFlush(com1);
//		Pretty.println(companyService.getAll(), new PrettyConvertorConfig(PrettyAccessor.Private, false, false));
	}

}
