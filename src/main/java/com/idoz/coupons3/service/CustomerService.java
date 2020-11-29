package com.idoz.coupons3.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerService extends ClientService {

	private int customerId;

	@Override
	public boolean login(String email, String password) {
		if (customerRepo.existsCustomerByEmailAndPassword(email, password)) {
			this.customerId = customerRepo.findByEmailAndPassword(email, password).getId();
			return true;
		}
		return false;
	}

	public void purchaseCoupon(Coupon coupon)
			throws DataManipulationException, WrongIdException, DetailDuplicationException {
		if (couponRepo.existsById(coupon.getId()) == false) {
			throw new WrongIdException("coupon id does not exist");
		}
		Coupon dbCoupon = couponRepo.getOne(coupon.getId());
		if (getCustomerCoupons().contains(dbCoupon)) {
			throw new DataManipulationException("customer already has coupon");
		}
		if (dbCoupon.getAmount() <= 0) {
			throw new DataManipulationException("coupon with amount 0");
		}
		if (dbCoupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
			throw new DataManipulationException("coupon is outdated");
		}
		Customer customer = customerRepo.getOne(this.customerId);
		customer.purchaseCoupon(dbCoupon);
		customerRepo.saveAndFlush(customer);
		dbCoupon.setAmount(dbCoupon.getAmount() - 1);
		couponRepo.saveAndFlush(dbCoupon);
	}

	public Set<Coupon> getCustomerCoupons() {
		return customerRepo.getOne(this.customerId).getCoupons();
	}

	public Set<Coupon> getCustomerCoupons(Category category) {
		Set<Coupon> coupons = getCustomerCoupons();
		Set<Coupon> result = new HashSet<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategory().equals(category)) {
				result.add(coupon);
			}
		}
		return result;
	}

	public Set<Coupon> getCustomerCoupons(double maxPrice) {
		Set<Coupon> coupons = getCustomerCoupons();
		Set<Coupon> result = new HashSet<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= maxPrice) {
				result.add(coupon);
			}
		}
		return result;
	}

	public Customer getCustomerDetails() {
		return customerRepo.getOne(this.customerId);
	}

}
