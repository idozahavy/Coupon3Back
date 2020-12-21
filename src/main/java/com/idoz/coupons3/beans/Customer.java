package com.idoz.coupons3.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	@ManyToMany
	private Set<Coupon> coupons;

	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public boolean purchaseCoupon(Coupon coupon) {
		if (coupons == null) {
			coupons = new HashSet<Coupon>();
		}
		return coupons.add(coupon);
	}

	public boolean removeCoupon(Coupon coupon) {
		if (coupons == null) {
			return false;
		}
		return coupons.removeIf((Coupon cp) -> cp.getId() == coupon.getId());
	}
}
