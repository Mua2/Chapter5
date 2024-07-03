package com.sparta.dailyswitter.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowResponseDto {
	private String message;

	public FollowResponseDto(String message) {
		this.message = message;
	}
}
