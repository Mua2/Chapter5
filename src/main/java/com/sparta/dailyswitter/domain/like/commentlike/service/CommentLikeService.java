package com.sparta.dailyswitter.domain.like.commentlike.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.comment.service.CommentService;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLike;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLikeId;
import com.sparta.dailyswitter.domain.like.commentlike.repository.CommentLikeRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
	private final CommentLikeRepository commentLikeRepository;
	private final PostService postService;
	private final CommentService commentService;

	@Transactional
	public void createCommentLike(Long postId, Long commentId, User user) {
		Comment comment = commentService.findById(commentId);
		Post post = postService.findById(postId);

		CommentLikeId commentLikeId = CommentLikeId.builder()
			.user(user)
			.comment(comment)
			.build();

		if (commentLikeRepository.findById(commentLikeId).isPresent()) {
			throw new CustomException(ErrorCode.COMMENT_LIKE_EXIST);
		}

		commentService.checkCommentPostNotFound(comment, post);
		commentService.checkCommentUserFound(comment, user);


		CommentLike commentLike = CommentLike.builder()
			.id(commentLikeId)
			.build();

		commentLikeRepository.save(commentLike);
		comment.addCommentLikes();
	}

	@Transactional
	public void deleteCommentLike(Long postId, Long commentId, User user) {
		Comment comment = commentService.findById(commentId);
		Post post = postService.findById(postId);

		CommentLikeId commentLikeId = CommentLikeId.builder()
			.user(user)
			.comment(comment)
			.build();

		if (commentLikeRepository.findById(commentLikeId).isEmpty()) {
			throw new CustomException(ErrorCode.COMMENT_LIKE_NOT_EXIST);
		}

		commentService.checkCommentPostNotFound(comment, post);
		commentService.checkCommentUserFound(comment, user);


		CommentLike commentLike = CommentLike.builder()
			.id(commentLikeId)
			.build();

		commentLikeRepository.delete(commentLike);
		comment.subCommentLikes();
	}
}
