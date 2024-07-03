package com.sparta.dailyswitter.domain.user.dto;

import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoleChangeRequestDto {

	private UserRoleEnum role;
}
