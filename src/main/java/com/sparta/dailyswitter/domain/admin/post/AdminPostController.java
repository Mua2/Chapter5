package com.sparta.dailyswitter.domain.admin.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminPostController {

	private final PostService postService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/posts")
	public ResponseEntity<Page<PostResponseDto>> getAllPosts(
		@RequestParam(defaultValue = "0") int page) {

		Pageable pageable = PageRequest.of(page, 5);
		return ResponseEntity.ok().body(postService.getAllPosts(pageable));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(
		@PathVariable Long postId,
		@RequestBody PostRequestDto requestDto) {

		PostResponseDto responseDto = postService.AdminUpdatePost(postId, requestDto);
		return ResponseEntity.ok().body(responseDto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePost(
		@PathVariable Long postId) {

		postService.AdminDeletePost(postId);
		return ResponseEntity.ok("게시물이 삭제되었습니다.");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/posts/{postId}/pin")
	public ResponseEntity<PostResponseDto> pinPost(
		@PathVariable Long postId) {

		PostResponseDto pinnedPost = postService.togglePinPost(postId);
		return ResponseEntity.ok().body(pinnedPost);
	}
}
