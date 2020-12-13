package com.idoz.coupons3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idoz.coupons3.beans.Category;
import com.idoz.coupons3.beans.Company;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyService extends ClientService {

	private int companyId;

	@Override
	public boolean login(String email, String password) {
		if (companyRepo.existsCompanyByEmailAndPassword(email, password)) {
			companyId = companyRepo.findByEmailAndPassword(email, password).getId();
			return true;
		}
		return false;
	}

	public void addCoupon(Coupon coupon) throws DetailDuplicationException, WrongIdException {
		coupon.setId(0);
		if (couponRepo.existsCouponByCompanyIdAndTitle(this.companyId, coupon.getTitle())) {
			throw new DetailDuplicationException("coupon title already exist in company");
		}

		Company company = companyRepo.getOne(this.companyId);
		company.addCoupon(coupon);
		companyRepo.saveAndFlush(company);
	}

	public void updateCoupon(Coupon coupon) throws WrongIdException, DataManipulationException {
		if (couponRepo.existsById(coupon.getId()) == false) {
			throw new WrongIdException("coupon id does not exist");
		}
		Coupon dbCoupon = couponRepo.getOne(coupon.getId());
		if (dbCoupon.getCompanyId() != companyId) {
			throw new WrongIdException("coupon does not belong to company");
		}
		if (dbCoupon.getCompanyId() != coupon.getCompanyId()) {
			throw new DataManipulationException("can not change coupon comapny id");
		}
		couponRepo.saveAndFlush(coupon);
	}

	public void deleteCoupon(int couponId) throws WrongIdException {
		if (couponRepo.existsById(couponId) == false) {
			throw new WrongIdException("coupon id does not exist");
		}
		Coupon dbCoupon = couponRepo.getOne(couponId);
		if (dbCoupon.getCompanyId() != companyId) {
			throw new WrongIdException("coupon does not belong to company");
		}
		List<Customer> customersWithCoupon = customerRepo.findByCouponsContaining(dbCoupon);
		for (Customer customer : customersWithCoupon) {
			customer.removeCoupon(dbCoupon);
			customerRepo.saveAndFlush(customer);
		}
		Company company = companyRepo.getOne(companyId);
		company.removeCoupon(dbCoupon);
		companyRepo.saveAndFlush(company);
		couponRepo.delete(dbCoupon);
	}

	public List<Coupon> getCompanyCoupons() {
		return companyRepo.getOne(this.companyId).getCoupons();
	}

	public List<Coupon> getCompanyCoupons(Category category) {
		List<Coupon> coupons = getCompanyCoupons();
		List<Coupon> result = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategory().equals(category)) {
				result.add(coupon);
			}
		}
		return result;
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		List<Coupon> coupons = getCompanyCoupons();
		List<Coupon> result = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= maxPrice) {
				result.add(coupon);
			}
		}
		return result;
	}

	public Company getCompanyDetails() {
		return companyRepo.getOne(this.companyId);
	}

}
