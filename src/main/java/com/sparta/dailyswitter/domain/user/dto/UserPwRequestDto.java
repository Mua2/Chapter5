package com.sparta.dailyswitter.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPwRequestDto {
	@Pattern(
		regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
		message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
	)
	private String currentPassword;
	@Pattern(
		regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
		message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
	)
	private String newPassword;
}
