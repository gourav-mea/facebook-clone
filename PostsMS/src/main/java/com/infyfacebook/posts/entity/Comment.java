package com.infyfacebook.posts.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.infyfacebook.posts.dto.CommentDTO;


@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long commentId;
    private Long postId;
    private Long commentBy;
    private String commentText;
    @Column(name = "TIMESTAMP_COLUMN")
	private Timestamp timestampColumn;
    
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getCommentBy() {
		return commentBy;
	}
	public void setCommentBy(Long commentBy) {
		this.commentBy = commentBy;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Timestamp getTimestampColumn() {
		return timestampColumn;
	}
	public void setTimestampColumn(Timestamp timestampColumn) {
		this.timestampColumn = timestampColumn;
	}
    
	public CommentDTO toDTO() {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setCommentBy(commentBy);
		commentDTO.setCommentId(commentId);
		commentDTO.setCommentText(commentText);
		commentDTO.setPostId(postId);
		commentDTO.setTimestampColumn(timestampColumn);
		return commentDTO;
	}
}
