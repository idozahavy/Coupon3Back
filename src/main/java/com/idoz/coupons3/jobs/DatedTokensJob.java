package com.idoz.coupons3.jobs;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.idoz.coupons3.security.TokenManager;
import com.idoz.coupons3.security.beans.ServiceData;

import lombok.AllArgsConstructor;

@Component
@EnableScheduling
@AllArgsConstructor
public class DatedTokensJob {

	private static final int DELETE_TOKEN_AFTER_MS = (int) (0.5 * 3600 * 1000); // 30 Minutes \ 0.5 Hour
	private static final int CHECK_TOKEN_INTERVAL_MS = 10 * 60 * 1000; // 10 Minutes

	private TokenManager tokenManager;

	@Scheduled(fixedRate = CHECK_TOKEN_INTERVAL_MS)
	public void checkTokens() {
		System.out.println("checkTokens...");
		long nowMs = System.currentTimeMillis();
		Map<String, ServiceData> map = tokenManager.getTokenMap();
		ArrayList<Entry<String, ServiceData>> obs = new ArrayList<>(map.entrySet());
		obs.stream().forEach((item) -> {
			ServiceData itemData = item.getValue();
			long itemTimeSpan = nowMs - itemData.getCreated().getTime();
			if (itemTimeSpan >= DELETE_TOKEN_AFTER_MS) {
				tokenManager.removeService(item.getKey());
				System.out.println(String.format("Token '%s' Deleted", item.getKey()));
			}
		});
	}

}
