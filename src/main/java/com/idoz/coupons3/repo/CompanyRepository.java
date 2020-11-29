package com.idoz.coupons3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idoz.coupons3.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	boolean existsCompanyByEmailAndPassword(String email, String password);

	Company findByEmailAndPassword(String email, String password);

	boolean existsCompanyByNameOrEmail(String name, String email);

}
