package com.idoz.coupons3.jobs;

import java.io.*;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.beans.*;
import com.idoz.coupons3.repo.*;

@Component
@EnableScheduling
public class DatedCouponsJob implements Runnable {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	private static final String FILE_NAME = "last_date_coupons_cleared.txt";

	/*
	 * reads file 'FILE_NAME' and finds the last date that coupons deletion has occured, if
	 * before today starts deleting outdated coupons.
	 */
	@Scheduled(fixedRate = 30*60*1000)
	public void run() {
		synchronized (this.getClass()) {
			System.out.println("running DatedCouponsJob...");
			final String lastDateString = readFile();
			if (lastDateString == null || LocalDate.now().isAfter(LocalDate.parse(lastDateString))) {
				System.out.println("Clearing outdated coupons...");
				clearOutdatedCoupons();
				writeNowToFile();
			}
		}
	}

	private void clearOutdatedCoupons() {
		List<Coupon> coupons = couponRepository.findAll();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
				Company company = companyRepository.getOne(coupon.getCompanyId());
				List<Customer> customersWithCoupon = customerRepository.findByCouponsContaining(coupon);
				for (Customer customer : customersWithCoupon) {
					customer.removeCoupon(coupon);
					customerRepository.saveAndFlush(customer);
				}
				company.removeCoupon(coupon);
				companyRepository.saveAndFlush(company);
			}
		}
	}

	private void writeNowToFile() {
		File file = new File(FILE_NAME);
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(LocalDate.now().toString());
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFile() {
		try {
			File file = new File(FILE_NAME);
			file.createNewFile();
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String lastDateString = bufferedReader.readLine();
			bufferedReader.close();
			return lastDateString;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
