package com.sparta.dailyswitter.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sparta.dailyswitter.domain.post.controller.PostController;
import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

class PostControllerTest {

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

		ResponseEntity<?> responseEntity = postController.createPost(requestDto, userDetails);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
