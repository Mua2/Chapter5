package com.sparta.dailyswitter.domain.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserPwRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.dto.UserRoleChangeRequestDto;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserResponseDto getUser(Long id) {
		User user = findUser(id);

		return new UserResponseDto(user);
	}

	public List<UserResponseDto> getAllUsers() {
		List<User> users = userRepository.findAll();

		return users.stream()
			.map(UserResponseDto::new)
			.toList();
	}

	public UserResponseDto updateUserInfo(Long id, UserInfoRequestDto userInfoRequestDto) {
		User user = findUser(id);

		user.updateUserInfo(userInfoRequestDto);
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	public UserResponseDto updatePassword(Long id, UserPwRequestDto userPwRequestDto) {
		User user = findUser(id);

		if (!passwordEncoder.matches(userPwRequestDto.getCurrentPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
		}

		if (user.isPasswordInHistory(userPwRequestDto.getNewPassword(), passwordEncoder)) {
			throw new CustomException(ErrorCode.DUPLICATE_PASSWORD);
		}

		user.updatePassword(passwordEncoder.encode(userPwRequestDto.getNewPassword()));
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	public UserResponseDto updateUserRole(Long id, UserRoleChangeRequestDto userRoleChangeRequestDto) {
		User user = findUser(id);
		user.updateStatus(userRoleChangeRequestDto.getRole());
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public UserResponseDto toggleBlockStatus(Long id) {
		User user = findUser(id);
		user.toggleBlock();
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	private User findUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}

