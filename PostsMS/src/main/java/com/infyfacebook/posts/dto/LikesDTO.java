package com.infyfacebook.posts.dto;

import java.sql.Timestamp;

import com.infyfacebook.posts.entity.Likes;

public class LikesDTO {
	private Long likeId;
    private Long postId;
    private Long likedBy;
	private Timestamp timestampColumn;
	public Long getLikeId() {
		return likeId;
	}
	public void setLikeId(Long likeId) {
		this.likeId = likeId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(Long likedBy) {
		this.likedBy = likedBy;
	}
	public Timestamp getTimestampColumn() {
		return timestampColumn;
	}
	public void setTimestampColumn(Timestamp timestampColumn) {
		this.timestampColumn = timestampColumn;
	}
	
	public Likes toEntity() {
		Likes likes = new Likes();
		likes.setLikedBy(likedBy);
		likes.setLikeId(likeId);
		likes.setPostId(postId);
		likes.setTimestampColumn(timestampColumn);
		return likes;
	}
}
