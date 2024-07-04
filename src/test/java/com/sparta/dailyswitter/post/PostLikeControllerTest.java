package com.sparta.dailyswitter.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sparta.dailyswitter.domain.like.postlike.controller.PostLikeController;
import com.sparta.dailyswitter.domain.like.postlike.service.PostLikeService;
import com.sparta.dailyswitter.domain.post.controller.PostController;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.security.UserDetailsImpl;

public class PostLikeControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(PostLikeControllerTest.class);

	@Mock
	private PostLikeService postLikeService;

	@Mock
	private PostService postService;

	@InjectMocks
	private PostController postController;

	@InjectMocks
	private PostLikeController postLikeController;

	private User user;
	private UserDetailsImpl userDetails;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		user = User.builder()
			.id(1L)
			.userId("testuser")
			.build();

		userDetails = new UserDetailsImpl(user);
	}

	@Test
	void createPostLikeTest() {
		doNothing().when(postLikeService).createPostLike(anyLong(), any(User.class));

		ResponseEntity<String> response = postLikeController.createPostLikes(1L, userDetails);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("게시물 좋아요가 등록되었습니다.", response.getBody());
		logger.info("좋아요 생성에 성공했습니다.");

		verify(postLikeService, times(1)).createPostLike(anyLong(), any(User.class));
	}

	@Test
	void deletePostLikeTest() {
		doNothing().when(postLikeService).deletePostLike(anyLong(), any(User.class));

		ResponseEntity<String> response = postLikeController.deletePostLikes(1L, userDetails);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("게시물 좋아요가 삭제되었습니다.", response.getBody());
		logger.info("좋아요 삭제에 성공했습니다.");

		verify(postLikeService, times(1)).deletePostLike(anyLong(), any(User.class));
	}

	@Test
	void getPostLikeTest() {
		Long postId = 1L;
		PostResponseDto postResponseDto = PostResponseDto.builder()
			.title("Test Title")
			.contents("Test Contents")
			.userId("testuser")
			.postLikes(5L)
			.isPinned(false)
			.createdAt(null)
			.updatedAt(null)
			.build();
		when(postService.getPost(postId)).thenReturn(postResponseDto);

		ResponseEntity<?> response = postController.getPost(postId);

		assertNotNull(response, "응답이 Null 입니다.");
		assertEquals(HttpStatus.OK, response.getStatusCode(), "응답 상태가 200OK가 아닙니다.");
		assertNotNull(response.getBody(), "응답 본문이 Null입니다.");

		PostResponseDto responseDto = (PostResponseDto) response.getBody();
		assertEquals(5L, responseDto.getPostLikes(), "좋아요 개수가 일치하지 않습니다.");
		assertEquals("Test Title", responseDto.getTitle(), "제목이 일치하지 않습니다.");
		assertEquals("Test Contents", responseDto.getContents(), "내용이 일치하지 않습니다.");
		assertEquals("testuser", responseDto.getUserId(), "사용자 ID 가 일치하지 않습니다.");

		logger.info("게시물 조회 테스트 성공 - 좋아요 개수: {}", responseDto.getPostLikes());
	}
}
