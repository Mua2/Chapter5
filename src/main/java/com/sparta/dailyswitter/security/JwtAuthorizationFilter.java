package com.sparta.dailyswitter.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String tokenValue = jwtUtil.getJwtFromHeader(request);

		log.info(tokenValue);
		if (tokenValue != null && !tokenValue.isEmpty()) {
			if (!jwtUtil.validateToken(tokenValue)) {
				log.error("Token Error");
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "유효하지 않은 토큰입니다.");
				return;
			}
			Claims claims = jwtUtil.getUserInfoFromToken(tokenValue);
			log.info("Subject: " + claims.getSubject());
			String userId = claims.getSubject();

			User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
			if (!user.isExist()) {
				throw new CustomException(ErrorCode.USER_NOT_FOUND);
			}
			setAuthentication(claims.getSubject());
		}
		filterChain.doFilter(request, response);
	}

	private void setAuthentication(String username) {
		UserDetailsImpl userDetails = (UserDetailsImpl)userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
