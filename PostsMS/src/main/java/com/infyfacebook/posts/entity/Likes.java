package com.infyfacebook.posts.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.infyfacebook.posts.dto.LikesDTO;

@Entity
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	    
	private Long likeId;
    private Long postId;
    private Long likedBy;
    @Column(name = "TIMESTAMP_COLUMN") 
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
    
	public LikesDTO toDTO() {
		LikesDTO likesDTO = new LikesDTO();
		likesDTO.setLikedBy(likedBy);
		likesDTO.setLikeId(likeId);
		likesDTO.setPostId(postId);
		likesDTO.setTimestampColumn(timestampColumn);
		return likesDTO;
	}
}
