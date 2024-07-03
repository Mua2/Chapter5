package com.sparta.dailyswitter.domain.admin.comment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.dto.CommentResponseDto;
import com.sparta.dailyswitter.domain.comment.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminCommentController {

	private final CommentService commentService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/comments")
	public ResponseEntity<List<CommentResponseDto>> getAllComments() {
		return ResponseEntity.ok().body(commentService.getAllComments());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentResponseDto> adminUpdateComment(
		@PathVariable Long commentId,
		@RequestBody @Valid CommentRequestDto requestDto) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.adminUpdateComment(commentId, requestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment(
		@PathVariable Long commentId) {
		commentService.adminDeleteComment(commentId);
		return ResponseEntity.ok("댓글이 삭제되었습니다.");
	}
}
