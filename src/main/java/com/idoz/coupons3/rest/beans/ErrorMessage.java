package com.idoz.coupons3.rest.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
		
	public ErrorMessage(Exception e) {
		message = e.getMessage();
	}

	private String message;
	
}
