package com.idoz.coupons3.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.idoz.coupons3.security.beans.ServiceData;

@Configuration
public class BeanConfigurator {

	@Bean
	public Map<String, ServiceData> clientServiceMap() {
		return new HashMap<>();
	}

}
