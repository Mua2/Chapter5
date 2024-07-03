package com.sparta.dailyswitter.domain.admin.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.dto.UserRoleChangeRequestDto;
import com.sparta.dailyswitter.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminUserController {

	private final UserService userService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(
		@PathVariable Long userId,
		@RequestBody UserInfoRequestDto userInfoRequestDto) {

		return ResponseEntity.ok().body(userService.updateUserInfo(userId, userInfoRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(
		@PathVariable Long userId) {

		userService.deleteUser(userId);
		return ResponseEntity.ok("사용자가 삭제되었습니다.");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/{userId}/role")
	public ResponseEntity<UserResponseDto> updateUserRole(
		@PathVariable Long userId,
		@RequestBody UserRoleChangeRequestDto userRoleChangeRequestDto) {
		;
		return ResponseEntity.ok().body(userService.updateUserRole(userId, userRoleChangeRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/{userId}/block")
	public ResponseEntity<UserResponseDto> toggleBlockStatus(
		@PathVariable Long userId) {

		return ResponseEntity.ok().body(userService.toggleBlockStatus(userId));
	}
}
