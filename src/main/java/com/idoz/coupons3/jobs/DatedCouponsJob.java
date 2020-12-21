package com.idoz.coupons3.jobs;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.repo.CouponRepository;
import com.idoz.coupons3.repo.CustomerRepository;

@Component
@EnableScheduling
public class DatedCouponsJob implements Runnable {

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private static Date lastDate;

	@Override
	@Scheduled(fixedRate = 1 * 3600 * 1000) // 1 Hour
	public void run() {
		synchronized (this.getClass()) {
			System.out.println("running DatedCouponsJob check...");
			Date nowDate = new Date(System.currentTimeMillis());
			LocalDate now = nowDate.toLocalDate();
			if (lastDate == null || now.isAfter(lastDate.toLocalDate())) {
				System.out.println("Clearing outdated coupons...");
				clearOutdatedCoupons();
				lastDate = nowDate;
				// TODO consider async request to:
				// 1. spring async
				// 2. spring messaging/kafka/rabbitMQ
			}
		}
	}

	private void clearOutdatedCoupons() {
		List<Coupon> coupons = couponRepository.findAll();
		Date now = new Date(System.currentTimeMillis());
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(now)) {
				System.out.println("coupon deletion - " + coupon.toString());
				List<Customer> customersWithCoupon = customerRepository.findByCouponsContaining(coupon);
				for (Customer customer : customersWithCoupon) {
					customer.removeCoupon(coupon);
					customerRepository.saveAndFlush(customer);
				}
				couponRepository.delete(coupon);
			}
		}
	}
}
