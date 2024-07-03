package com.sparta.dailyswitter.domain.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.auth.dto.LoginRequestDto;
import com.sparta.dailyswitter.domain.auth.dto.LoginResponseDto;
import com.sparta.dailyswitter.domain.auth.dto.SignoutRequestDto;
import com.sparta.dailyswitter.domain.auth.dto.SignupRequestDto;
import com.sparta.dailyswitter.domain.auth.service.AuthService;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody SignupRequestDto requestDto) throws IOException {
		return ResponseEntity.ok(authService.signup(requestDto));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
		LoginResponseDto responseDto = authService.login(requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/logout")
	public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		authService.logout(userDetails.getUser());
		return ResponseEntity.ok("로그아웃이 완료되었습니다.");
	}

	@PutMapping("/signout")
	public ResponseEntity<String> signout(@RequestBody SignoutRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		authService.signout(requestDto, userDetails.getUser());
		return ResponseEntity.ok("탈퇴가 완료되었습니다.");
	}
}