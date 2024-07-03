package com.sparta.dailyswitter.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

	private String token;
	private String refreshToken;
	private String message;

	public LoginResponseDto(String token, String refreshToken, String message) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.message = message;
	}

}
