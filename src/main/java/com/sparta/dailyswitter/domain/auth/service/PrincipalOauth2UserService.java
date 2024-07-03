package com.sparta.dailyswitter.domain.auth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.domain.auth.userinfo.KakaoUserInfo;
import com.sparta.dailyswitter.domain.auth.userinfo.NaverUserInfo;
import com.sparta.dailyswitter.domain.auth.userinfo.OAuth2UserInfo;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;
import com.sparta.dailyswitter.security.JwtUtil;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("getAttributes : {}", oAuth2User.getAttributes());

		OAuth2UserInfo oAuth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();

		if ("kakao".equals(provider)) {
			log.info("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>) oAuth2User.getAttributes());
		} else if ("naver".equals(provider)) {
			log.info("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
		}

		if (oAuth2UserInfo == null) {
			log.error("지원하지 않는 제공자: " + provider);
			throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
		}

		String providerId = oAuth2UserInfo.getProviderId();
		String loginId = provider + "_" + providerId;
		String nickname = oAuth2UserInfo.getName();
		String email = oAuth2UserInfo.getEmail();

		Optional<User> optionalUser = userRepository.findByUserId(loginId);
		User user = optionalUser.orElseGet(() -> User.builder()
			.userId(loginId)
			.username(nickname)
			.email(email)
			.provider(provider)
			.providerId(providerId)
			.role(UserRoleEnum.USER)
			.build());

		if ("naver".equals(provider)) {
			user.setNaverId(providerId);
		} else if ("kakao".equals(provider)) {
			user.setKakaoId(providerId);
		}

		log.info("JWT 토큰 생성 중...");
		String accessToken = jwtUtil.createToken(user.getUserId());
		String refreshToken = jwtUtil.createRefreshToken(user.getUserId());

		user.updateAccessToken(accessToken);
		user.updateRefresh(refreshToken);
		userRepository.save(user);

		log.info("JWT 토큰 생성 및 사용자 정보 저장 완료");
		UserDetailsImpl userDetails = new UserDetailsImpl(user, oAuth2User.getAttributes());
		userDetails.setAccessToken(accessToken);
		userDetails.setRefreshToken(refreshToken);

		return userDetails;
	}
}
