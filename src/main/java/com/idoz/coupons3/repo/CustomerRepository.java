package com.idoz.coupons3.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idoz.coupons3.beans.Coupon;
import com.idoz.coupons3.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsCustomerByEmailAndPassword(String email, String password);

	Customer findByEmailAndPassword(String email, String password);

	List<Customer> findByCouponsContaining(Coupon coupon);

	boolean existsCustomerByEmail(String email);

}
