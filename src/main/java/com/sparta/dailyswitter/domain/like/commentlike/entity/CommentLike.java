package com.sparta.dailyswitter.domain.like.commentlike.entity;

import com.sparta.dailyswitter.common.util.Timestamped;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment_like")
@NoArgsConstructor
public class CommentLike extends Timestamped {

	@EmbeddedId
	private CommentLikeId id;

	@Builder
	public CommentLike (CommentLikeId id) {
		this.id = id;
	}
}
