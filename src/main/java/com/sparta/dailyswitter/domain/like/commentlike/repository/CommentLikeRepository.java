package com.sparta.dailyswitter.domain.like.commentlike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLike;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLikeId;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {

}
