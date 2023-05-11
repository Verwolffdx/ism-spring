package com.edatwhite.smkd.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String fio;
	private String username;
	private List<String> roles;
	private List<Long> divisions;

	public JwtResponse(String accessToken, Long id, String fio, String username, List<String> roles, List<Long> divisions) {
		this.token = accessToken;
		this.id = id;
		this.fio = fio;
		this.username = username;
		this.roles = roles;
		this.divisions = divisions;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public List<Long> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<Long> divisions) {
		this.divisions = divisions;
	}
}
