package com.sparta.dailyswitter.domain.auth.userinfo;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {

	public KakaoUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getName() {
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
		if (properties == null) {
			return null;
		}
		return (String)properties.get("nickname");
	}

	@Override
	public String getEmail() {
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		if (kakaoAccount == null) {
			return null;
		}
		return (String)kakaoAccount.get("email");
	}
}
