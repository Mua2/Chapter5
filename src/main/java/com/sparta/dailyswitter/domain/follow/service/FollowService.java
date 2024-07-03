package com.sparta.dailyswitter.domain.follow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.follow.entity.Follow;
import com.sparta.dailyswitter.domain.follow.repository.FollowRepository;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private final FollowRepository followRepository;
	private final UserRepository userRepository;

	@Transactional
	public void followUser(Long followerUserId, Long followingUserId) {
		User followerUser = userRepository.findById(followerUserId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
		User followingUser = userRepository.findById(followingUserId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
		if (followRepository.findByFollowerUserAndFollowingUser(followerUser, followingUser) == null) {
			Follow follow = Follow.builder()
				.followerUser(followerUser)
				.followingUser(followingUser)
				.build();
			followRepository.save(follow);
		} else {
			throw new CustomException(ErrorCode.USER_NOT_UNIQUE);
		}
	}

	@Transactional
	public void unfollowUser(Long followerUserId, Long followingUserId) {
		User followerUser = userRepository.findById(followerUserId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
		User followingUser = userRepository.findById(followingUserId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
		Follow follow = followRepository.findByFollowerUserAndFollowingUser(followerUser, followingUser);
		if (follow != null) {
			followRepository.delete(follow);
		} else {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
	}

	public List<User> getFollows(User followerUser) {
		List<Follow> follows = followRepository.findAllByFollowerUser(followerUser);
		if (follows.isEmpty()) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}
		return follows.stream().map(Follow::getFollowingUser).collect(Collectors.toList());
	}
}