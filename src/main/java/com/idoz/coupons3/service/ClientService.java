package com.idoz.coupons3.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.idoz.coupons3.repo.CompanyRepository;
import com.idoz.coupons3.repo.CouponRepository;
import com.idoz.coupons3.repo.CustomerRepository;

public abstract class ClientService {
	@Autowired
	protected CouponRepository couponRepo;
	@Autowired
	protected CompanyRepository companyRepo;
	@Autowired
	protected CustomerRepository customerRepo;

	public abstract boolean login(String email, String password);
}
