package com.idoz.coupons3.security.beans;

import java.sql.Date;

import com.idoz.coupons3.service.ClientService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceData {

	Date created;
	ClientService service;

	public ServiceData(ClientService service) {
		this.service = service;
		this.created = new Date(System.currentTimeMillis());
	}

}
