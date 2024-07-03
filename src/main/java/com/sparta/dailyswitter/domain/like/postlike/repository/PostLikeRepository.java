package com.sparta.dailyswitter.domain.like.postlike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.dailyswitter.domain.like.postlike.entity.PostLike;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLikeId;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
}
