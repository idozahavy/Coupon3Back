package com.idoz.coupons3.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;

import com.idoz.coupons3.service.ClientService;

public class BeanConfigurator {

	@Bean
	public Map<String, ClientService> clientServiceMap() {
		return new HashMap<>();
	}
	
	@Bean
	public Map<String, String> emailTokenMap() {
		return new HashMap<>();
	}

}
