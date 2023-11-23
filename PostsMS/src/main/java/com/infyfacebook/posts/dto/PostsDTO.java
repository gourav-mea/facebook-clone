package com.infyfacebook.posts.dto;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.infyfacebook.posts.entity.Posts;


public class PostsDTO {
	
	private Long postId;
	@NotNull(message= "{post.userid.notpresent}")
	private Long userId;
	private String postText;
	private String postImage;
	private byte[] postVideo;
	private Timestamp timestampColumn;
	@NotNull(message= "{post.privacysetting.notpresent}")
	private PrivacySetting privacySetting;
	
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public String getPostImage() {
		return postImage;
	}
	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}
	public byte[] getPostVideo() {
		return postVideo;
	}
	public void setPostVideo(byte[] postVideo) {
		this.postVideo = postVideo;
	}
	public Timestamp getTimestampColumn() {
		return timestampColumn;
	}
	public void setTimestampColumn(Timestamp timestampColumn) {
		this.timestampColumn = timestampColumn;
	}
	public PrivacySetting getPrivacySetting() {
		return privacySetting;
	}
	public void setPrivacySetting(PrivacySetting privacySetting) {
		this.privacySetting = privacySetting;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(postVideo);
		result = prime * result + Objects.hash(postId, postImage, postText, privacySetting, timestampColumn, userId);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostsDTO other = (PostsDTO) obj;
		return Objects.equals(postId, other.postId) && Objects.equals(postImage, other.postImage)
				&& Objects.equals(postText, other.postText) && Arrays.equals(postVideo, other.postVideo)
				&& privacySetting == other.privacySetting && Objects.equals(timestampColumn, other.timestampColumn)
				&& Objects.equals(userId, other.userId);
	}
	
	public Posts toEntity(){
		Posts posts = new Posts();
		posts.setPostId(postId);
		posts.setPostImage(postImage);
		posts.setPostText(postText);
		posts.setPostVideo(postVideo);
		posts.setPrivacySetting(privacySetting);
		posts.setTimestampColumn(timestampColumn);
		posts.setUserId(userId);
		return posts;
	}
}
