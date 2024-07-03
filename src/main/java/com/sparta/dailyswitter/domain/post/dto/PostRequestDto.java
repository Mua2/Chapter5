package com.sparta.dailyswitter.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostRequestDto {
	private final String title;
	private final String contents;

	@Builder
	public PostRequestDto(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
}
