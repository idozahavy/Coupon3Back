package com.idoz.coupons3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.idoz.coupons3.security.ServiceInterceptor;
import com.idoz.coupons3.service.AdminService;
import com.idoz.coupons3.service.CompanyService;
import com.idoz.coupons3.service.CustomerService;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

	public ServiceInterceptor adminSI;
	public ServiceInterceptor companySI;
	public ServiceInterceptor customerSI;

	public InterceptorsConfig(ServiceInterceptor adminSI, ServiceInterceptor companySI, ServiceInterceptor customerSI) {
		this.adminSI = adminSI;
		this.adminSI.setServiceType(AdminService.class);
		this.companySI = companySI;
		this.companySI.setServiceType(CompanyService.class);
		this.customerSI = customerSI;
		this.customerSI.setServiceType(CustomerService.class);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(adminSI).addPathPatterns("/admin/**");
		registry.addInterceptor(companySI).addPathPatterns("/company/**");
		registry.addInterceptor(customerSI).addPathPatterns("/customer/**");
	}
}
