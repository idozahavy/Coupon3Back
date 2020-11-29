package com.idoz.coupons3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.idoz.coupons3.beans.Company;
import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;
import com.idoz.coupons3.exceptions.DataManipulationException;
import com.idoz.coupons3.exceptions.DetailDuplicationException;
import com.idoz.coupons3.exceptions.WrongIdException;

@Service
public class AdminService extends ClientService {

	@Override
	public boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");
	}

	public void addCompany(Company company) throws DetailDuplicationException {
		if (companyRepo.existsCompanyByNameOrEmail(company.getName(), company.getEmail())) {
			throw new DetailDuplicationException("company already exist with email or name");
		}
		companyRepo.save(company);
	}

	public void updateCompany(Company company) throws DataManipulationException, WrongIdException {
		if (companyRepo.existsById(company.getId()) == false) {
			throw new WrongIdException("company id does not exist");
		}
		Company dbComapny = companyRepo.getOne(company.getId());
		if (dbComapny.getName().equals(company.getName())) {
			companyRepo.saveAndFlush(company);
		} else {
			throw new DataManipulationException("can not change company name");
		}
	}

	public void deleteCompany(int companyId) throws WrongIdException {
		if (companyRepo.existsById(companyId) == false) {
			throw new WrongIdException("company id does not exist");
		}
		Company dbCompany = companyRepo.getOne(companyId);
		for (Coupon cp : dbCompany.getCoupons()) {
			List<Customer> customersWithCoupon = customerRepo.findByCouponsContaining(cp);
			for (Customer customer : customersWithCoupon) {
				customer.removeCoupon(cp);
			}
			couponRepo.delete(cp);
		}
		companyRepo.deleteById(companyId);
	}

	public List<Company> getAllCompanies() {
		return companyRepo.findAll();
	}

	public Company getOneCompany(int companyId) throws WrongIdException {
		if (companyRepo.existsById(companyId) == false) {
			throw new WrongIdException("company id does not exist");
		}
		return companyRepo.getOne(companyId);
	}

	public void addCustomer(Customer customer) throws DetailDuplicationException {
		if (customerRepo.existsCustomerByEmail(customer.getEmail())) {
			throw new DetailDuplicationException("can not add customer with same email as others");
		}
		customerRepo.save(customer);
	}

	public void updateCustomer(Customer customer) throws WrongIdException, DataManipulationException {
		if (customerRepo.existsById(customer.getId()) == false) {
			throw new WrongIdException("customer id does not exist");
		}
		customerRepo.saveAndFlush(customer);
	}

	public void deleteCustomer(int customerId) throws WrongIdException {
		if (customerRepo.existsById(customerId) == false) {
			throw new WrongIdException("customer id does not exist");
		}
		customerRepo.deleteById(customerId);
	}

	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	public Customer getOneCustomer(int customerId) throws WrongIdException {
		if (customerRepo.existsById(customerId) == false) {
			throw new WrongIdException("customer id does not exist");
		}
		return customerRepo.getOne(customerId);
	}

}
