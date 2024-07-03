package com.sparta.dailyswitter.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserPwRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.service.UserService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<UserResponseDto> getUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return ResponseEntity.ok().body(userService.getUser(userDetails.getUser().getId()));
	}

	@PutMapping
	public ResponseEntity<UserResponseDto> updateUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UserInfoRequestDto userInfoRequestDto) {

		return ResponseEntity.ok().body(userService.updateUserInfo(userDetails.getUser().getId(), userInfoRequestDto));
	}

	@PutMapping("/password")
	public ResponseEntity<UserResponseDto> updatePassword(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid UserPwRequestDto userPwRequestDto) {

		return ResponseEntity.ok().body(userService.updatePassword(userDetails.getUser().getId(), userPwRequestDto));
	}
}
