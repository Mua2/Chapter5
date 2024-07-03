package com.sparta.dailyswitter.domain.like.postlike.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLike;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLikeId;
import com.sparta.dailyswitter.domain.like.postlike.repository.PostLikeRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;
	private final PostService postService;

	@Transactional
	public void createPostLike(Long postId, User user) {
		Post post = postService.findById(postId);

		PostLikeId postLikeId = PostLikeId.builder()
			.user(user)
			.post(post)
			.build();

		if (postLikeRepository.findById(postLikeId).isPresent()) {
			throw new CustomException(ErrorCode.POST_LIKE_EXIST);
		}

		postService.checkPostUserFound(post, user);

		PostLike postLike = PostLike.builder()
			.id(postLikeId)
			.build();

		postLikeRepository.save(postLike);
		post.addPostLikes();
	}

	@Transactional
	public void deletePostLike(Long postId, User user) {
		Post post = postService.findById(postId);

		PostLikeId postLikeId = PostLikeId.builder()
			.user(user)
			.post(post)
			.build();

		if (postLikeRepository.findById(postLikeId).isEmpty()) {
			throw new CustomException(ErrorCode.POST_LIKE_NOT_EXIST);
		}

		postService.checkPostUserFound(post, user);

		PostLike postLike = PostLike.builder()
			.id(postLikeId)
			.build();

		postLikeRepository.delete(postLike);
		post.subPostLikes();
	}
}
