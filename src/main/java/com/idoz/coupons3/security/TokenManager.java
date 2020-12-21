package com.idoz.coupons3.security;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.idoz.coupons3.security.beans.ServiceData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@AllArgsConstructor
public class TokenManager {

	@Getter
	private Map<String, ServiceData> tokenMap;

	public synchronized String addService(ServiceData data) {
		for (Entry<String, ServiceData> item : tokenMap.entrySet()) {
			if (item.getValue().equals(data)) {
				return item.getKey();
			}
		}

		String token = UUID.randomUUID().toString();
		while (tokenMap.containsKey(token)) {
			token = UUID.randomUUID().toString();
		}
		tokenMap.put(token, data);
//		System.out.println(tokenMap);
		return token;
	}

	public synchronized void removeService(String token) {
		tokenMap.remove(token);
	}

	public ServiceData getService(String token) {
		if (tokenMap.containsKey(token)) {
			return tokenMap.get(token);
		}
		return null;
	}

	public boolean isExist(String token) {
		return tokenMap.containsKey(token);
	}

}
