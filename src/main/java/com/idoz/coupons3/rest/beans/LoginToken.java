package com.idoz.coupons3.rest.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginToken {

	private String token;
	private String clientType;
	
}
