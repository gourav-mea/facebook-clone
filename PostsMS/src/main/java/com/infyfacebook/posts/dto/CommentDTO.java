package com.infyfacebook.posts.dto;

import java.sql.Timestamp;

import com.infyfacebook.posts.entity.Comment;


public class CommentDTO {
	private Long commentId;
    private Long postId;
    private Long commentBy;
    private String commentText;
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
	
	public Comment toEntity() {
		Comment comment = new Comment();
		comment.setCommentBy(commentBy);
		comment.setCommentId(commentId);
		comment.setCommentText(commentText);
		comment.setPostId(postId);
		comment.setTimestampColumn(timestampColumn);
		return comment;
	}
	
}
