package com.idoz.coupons3.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;

	@Setter(value = AccessLevel.PRIVATE)
	@OneToMany(mappedBy = "companyId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Coupon> coupons;

	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public boolean addCoupon(Coupon coupon) {
		if (coupons == null) {
			coupons = new ArrayList<Coupon>();
		}
		coupon.setCompanyId(this.id);
		return coupons.add(coupon);
	}

	public boolean removeCoupon(Coupon coupon) {
		if (coupons == null) {
			return false;
		}
		return coupons.removeIf((Coupon cp) -> cp.getId() == coupon.getId());
	}
}
