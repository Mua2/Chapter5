package com.sparta.dailyswitter.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUtil {

	@Value("${JWT_SECRET_KEY}")
	private String secretKey;

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
	public static final String BEARER_PREFIX = "Bearer ";

	private Key getSigningKey() {
		byte[] keyBytes = secretKey.getBytes();
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
	}

	public String createToken(String userId) {
		Date now = new Date();
		Long minute = 30L * 60 * 1000;
		Date validity = new Date(now.getTime() + minute);

		return BEARER_PREFIX + Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken(String userId) {
		Date now = new Date();
		Long twoWeek = 14L * 24 * 60 * 60 * 1000;
		Date validity = new Date(now.getTime() + twoWeek);

		return BEARER_PREFIX + Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | SignatureException e) {
			throw new CustomException(ErrorCode.INVALID_SIGNATURE);
		} catch (UnsupportedJwtException e) {
			throw new CustomException(ErrorCode.UNSUPPORTED_TOKEN);
		} catch (IllegalArgumentException e) {
			throw new CustomException(ErrorCode.ILLEGAL_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new CustomException(ErrorCode.RELOGIN_REQUIRED);
		}
	}

	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public String substringToken(String token) {
		if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
			return token.substring(7);
		}
		return null;
	}

	public String getJwtFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		return substringToken(bearerToken);
	}

	public Boolean isTokenExpired(String token) {
		Claims claims = getUserInfoFromToken(token);
		Date date = claims.getExpiration();
		return date.before(new Date());
	}

	public Boolean isRefreshTokenExpired(String refreshToken) {
		String reToken = refreshToken.substring(7);
		Claims claims = getUserInfoFromToken(reToken);
		Date date = claims.getExpiration();
		return date.before(new Date());
	}
}
