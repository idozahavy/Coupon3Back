package com.idoz.coupons3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idoz.coupons3.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	boolean existsCouponByCompanyIdAndTitle(int companyId, String title);

}
