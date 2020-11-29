package com.idoz.coupons3.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.service.*;

@Component
public class LoginManager {

	@Autowired
	private ApplicationContext ctx;

	public <T extends ClientService> T login(String email, String password, Class<T> serviceClass) {
		if (ctx.containsBean(serviceClass.getName())) {
			throw new RuntimeException("Could not catch type " + serviceClass.getTypeName().toString());
		}
		T service = ctx.getBean(serviceClass);
		if (service.login(email, password)) {
			return service;
		}
		return null;
	}

}
