package com.gigadev.digitalmarketplace.auth.jwt;

import java.util.List;
import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String userName;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String userName, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.userName = userName;
		this.roles = roles;
	}
}
