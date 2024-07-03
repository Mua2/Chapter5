package com.sparta.dailyswitter.domain.follow.entity;

import com.sparta.dailyswitter.common.util.Timestamped;
import com.sparta.dailyswitter.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "follows")
public class Follow extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "following_user_id")
	private User followingUser;

	@ManyToOne
	@JoinColumn(name = "follower_user_id")
	private User followerUser;

	@Builder
	public Follow(User followingUser, User followerUser) {
		this.followingUser = followingUser;
		this.followerUser = followerUser;
	}
}
