package com.sparta.dailyswitter.domain.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.dto.CommentResponseDto;
import com.sparta.dailyswitter.domain.comment.service.CommentService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.createComment
			(postId, requestDto, userDetails.getUser()));
	}

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentResponseDto>> getComment(@PathVariable Long postId) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.getComment(postId));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId,
		@PathVariable Long commentId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.updateComment(postId, commentId, requestDto, userDetails.getUser()));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(postId, commentId, userDetails.getUser());
		return ResponseEntity.status(HttpStatus.OK)
			.body("댓글이 삭제되었습니다.");
	}
}
