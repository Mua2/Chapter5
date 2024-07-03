package com.sparta.dailyswitter.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignoutRequestDto {
	private String password;

	@Builder
	public SignoutRequestDto(String password) {
		this.password = password;
	}
}
