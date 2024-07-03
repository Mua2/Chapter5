package com.sparta.dailyswitter.security;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = jwtUtil.getJwtFromHeader(request);

		if (token != null && !token.isEmpty()) {
			if (jwtUtil.validateToken(token)) {
				Claims claims = jwtUtil.getUserInfoFromToken(token);
				request.setAttribute("username", claims.getSubject());
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "유효하지 않은 토큰입니다.");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}
