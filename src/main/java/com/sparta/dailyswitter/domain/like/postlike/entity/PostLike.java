package com.sparta.dailyswitter.domain.like.postlike.entity;


import com.sparta.dailyswitter.common.util.Timestamped;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_like")
@NoArgsConstructor
public class PostLike extends Timestamped {

	@EmbeddedId
	private PostLikeId id;

	@Builder
	public PostLike (PostLikeId id) {
		this.id = id;
	}
}
