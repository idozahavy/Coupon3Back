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

	public synchronized String addService(ClientService service) {
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

	public synchronized void removeService(String token) {
		tokenMap.remove(token);
	}

	public ClientService getService(String token) {
		if (tokenMap.containsKey(token)) {
			return tokenMap.get(token);
		}
		return null;
	}

	public boolean isExist(String token) {
		return tokenMap.containsKey(token);
	}

}
