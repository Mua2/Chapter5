package com.sparta.dailyswitter.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2LoginResponseDto {

	private String token;
	private String refreshToken;
}