package com.gigadev.digitalmarketplace.auth.jwt;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String userName;
	private List<String> roles;
	private Double accountBalance;
	private Boolean isSubscribed;

	public JwtResponse(String accessToken, Long id, String userName, List<String> roles,
			Double accountBalance, Boolean isSubscribed) {
		this.token = accessToken;
		this.id = id;
		this.userName = userName;
		this.roles = roles;
		this.accountBalance = accountBalance;
		this.isSubscribed = isSubscribed;
	}
}
