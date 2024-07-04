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

import com.sparta.dailyswitter.domain.post.controller.PostController;
import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

class PostControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(PostControllerTest.class);

	@Mock
	private PostService postService;

	@InjectMocks
	private PostController postController;

	@Mock
	private UserDetailsImpl userDetails;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createPostTest() {
		PostRequestDto requestDto = PostRequestDto.builder()
			.title("Test Title")
			.contents("Test Contents")
			.build();

		when(userDetails.getUsername()).thenReturn("testuser");
		when(postService.createPost(any(PostRequestDto.class), any(String.class)))
			.thenReturn(new PostResponseDto("Test Title", "Test Contents", "testuser", 0L, false, null, null));

		logger.info("제목이 '{}'인 게시물 생성 요청을 보냅니다.", requestDto.getTitle());

		ResponseEntity<?> responseEntity = postController.createPost(requestDto, userDetails);

		logger.info("응답을 받았습니다: {}", responseEntity);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "응답 상태 코드는 200 OK이어야 합니다");
		PostResponseDto responseDto = (PostResponseDto) responseEntity.getBody();
		assertNotNull(responseDto, "응답 본문은 null이 아니어야 합니다");
		assertEquals("Test Title", responseDto.getTitle(), "응답 제목은 요청 제목과 일치해야 합니다");
		assertEquals("Test Contents", responseDto.getContents(), "응답 내용은 요청 내용과 일치해야 합니다");
		assertEquals("testuser", responseDto.getUserId(), "응답 userId는 사용자 이름과 일치해야 합니다");
	}
}
