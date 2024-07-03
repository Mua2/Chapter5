package com.sparta.dailyswitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sparta.dailyswitter.security.JwtFilter;
import com.sparta.dailyswitter.security.JwtUtil;

@Configuration
public class FilterConfig {
	private final JwtUtil jwtUtil;

	@Autowired
	public FilterConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtFilter(jwtUtil));
		registrationBean.addUrlPatterns("/api/comment/*", "/api/post/*", "/api/user/*");
		return registrationBean;
	}
}
