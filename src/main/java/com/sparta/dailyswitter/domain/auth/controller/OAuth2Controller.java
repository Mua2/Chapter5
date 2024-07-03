package com.sparta.dailyswitter.domain.auth.controller;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sparta.dailyswitter.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/oauth2-login")
public class OAuth2Controller {

	@GetMapping("/success")
	public void loginSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
			UserDetailsImpl userDetails = (UserDetailsImpl) authToken.getPrincipal();
			String accessToken = userDetails.getAccessToken();
			String refreshToken = userDetails.getRefreshToken();

			String redirectUrl = "http://localhost:8080/success.html"
				+ "?accessToken=" + accessToken
				+ "&refreshToken=" + refreshToken;
			response.sendRedirect(redirectUrl);
		} else {
			response.sendRedirect("http://localhost:8080/login?error");
		}
	}

	@GetMapping("/failure")
	public String loginFailure() {
		return "redirect:/login?error";
	}

	@GetMapping("/logout")
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		response.sendRedirect("http://localhost:8080/login?logout");
	}

	@GetMapping("/login")
	public String loginPage() {
		return "redirect:/login.html";
	}
}
