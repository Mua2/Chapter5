package com.sparta.dailyswitter.domain.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentRequestDto {
	@NotEmpty(message = "댓글 내용을 작성해주세요.")
	private String content;
}
