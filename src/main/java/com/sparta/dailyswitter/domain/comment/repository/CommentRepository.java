package com.sparta.dailyswitter.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.dailyswitter.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(Long postId);
	List<Comment> findAllByOrderByCreatedAtDesc();
}
