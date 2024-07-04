package com.sparta.dailyswitter.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLike;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLikeId;
import com.sparta.dailyswitter.domain.like.postlike.repository.PostLikeRepository;
import com.sparta.dailyswitter.domain.like.postlike.service.PostLikeService;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.entity.User;

public class PostLikeServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(PostLikeServiceTest.class);

	@Mock
	private PostLikeRepository postLikeRepository;

	@Mock
	private PostService postService;

	@InjectMocks
	private PostLikeService postLikeService;

	private User user;
	private Post post;
	private PostLikeId postLikeId;
	private PostLike postLike;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		user = User.builder()
			.id(1L)
			.userId("testuser")
			.build();
		User anotherUser = User.builder()
			.id(2L)
			.userId("testuser2")
			.build();
		post = Post.builder()
			.title("Test Title")
			.contents("Test Contents")
			.user(user)
			.build();
		postLikeId = PostLikeId.builder()
			.user(user)
			.post(post)
			.build();
		postLike = PostLike.builder()
			.id(postLikeId)
			.build();
	}

	@Test
	void createPostLikeTest() {
		when(postService.findById(anyLong())).thenReturn(post);
		when(postLikeRepository.findById(any(PostLikeId.class))).thenReturn(Optional.empty());

		postLikeService.createPostLike(1L, user);

		verify(postLikeRepository, times(1)).save(any(PostLike.class));
		logger.info("좋아요 생성 성공. 테스트 통과.");
	}

	@Test
	void createPostLike_AlreadyExists() {
		when(postService.findById(anyLong())).thenReturn(post);
		when(postLikeRepository.findById(any(PostLikeId.class))).thenReturn(Optional.of(postLike));

		CustomException exception = assertThrows(CustomException.class,
			() -> postLikeService.createPostLike(1L, user));
		assertEquals(ErrorCode.POST_LIKE_EXIST, exception.getErrorCode());
		logger.info("좋아요가 이미 존재함. 테스트 통과.");
	}

	@Test
	void createPostLike_CannotLikeOwnPost() {
		Post ownPost = Post.builder()
			.title("My Title")
			.contents("My Contents")
			.user(user)
			.build();
		when(postService.findById(anyLong())).thenReturn(ownPost);

		CustomException exception = assertThrows(CustomException.class,
			() -> postLikeService.createPostLike(1L, user));
		assertEquals(ErrorCode.POST_SAME_USER, exception.getErrorCode());
		logger.info("자신의 게시물에 좋아요를 남길 수 없음. 테스트 성공.");
	}

	@Test
	void deletePostLike_NotExists() {
		when(postService.findById(anyLong())).thenReturn(post);
		when(postLikeRepository.findById(any(PostLikeId.class))).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class,
			() -> postLikeService.deletePostLike(1L, user));
		assertEquals(ErrorCode.POST_LIKE_NOT_EXIST, exception.getErrorCode());
		logger.info("존재하지 않는 좋아요는 삭제가 불가능 합니다. 테스트 성공.");
	}
}
