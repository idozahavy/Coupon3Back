package com.idoz.coupons3.security;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.idoz.coupons3.service.ClientService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenManager {

	private Map<String, ClientService> tokenMap;

	public String addService(ClientService service) {
		for (Entry<String, ClientService> item : tokenMap.entrySet()) {
			if (item.getValue().equals(service)) {
				return item.getKey();
			}
		}

		String token = UUID.randomUUID().toString();
		while (tokenMap.containsKey(token)) {
			token = UUID.randomUUID().toString();
		}
		tokenMap.put(token, service);
//		System.out.println(tokenMap);
		return token;
	}

	public void removeService(String token) {
		tokenMap.remove(token);
	}

	public ClientService getService(String token) {
		if (tokenMap.containsKey(token)) {
			return tokenMap.get(token);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends ClientService> T getService(String email, String token, Class<T> clazz) {
		if (tokenMap.containsKey(token)) {
			ClientService service = tokenMap.get(token);
			if (service != null && service.getClass().equals(clazz)) {
				return (T) service;
			}
		}
		return null;
	}

	public boolean isExist(String token) {
		return tokenMap.containsKey(token);
	}

}