package com.sparta.dailyswitter.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
	USER(Authority.USER),
	ADMIN(Authority.ADMIN),
	WITHDRAW(Authority.WITHDRAW);

	private final String authority;

	UserRoleEnum(String authority) { this.authority = authority; }

	public static class Authority {
		public static final String USER = "ROLE_USER";
		public static final String ADMIN = "ROLE_ADMIN";
		public static final String WITHDRAW = "ROLE_WITHDRAW";
	}
}

