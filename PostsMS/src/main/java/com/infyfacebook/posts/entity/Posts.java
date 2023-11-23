package com.infyfacebook.posts.entity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.infyfacebook.posts.dto.PostsDTO;
import com.infyfacebook.posts.dto.PrivacySetting;


@Entity
public class Posts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	private Long userId;
	private String postText;
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String postImage;
	@Lob
	private byte[] postVideo;
	@Column(name = "TIMESTAMP_COLUMN")
	private Timestamp timestampColumn;
	@Enumerated(EnumType.STRING)
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
		Posts other = (Posts) obj;
		return Objects.equals(postId, other.postId) && Objects.equals(postImage, other.postImage)
				&& Objects.equals(postText, other.postText) && Arrays.equals(postVideo, other.postVideo)
				&& privacySetting == other.privacySetting && Objects.equals(timestampColumn, other.timestampColumn)
				&& Objects.equals(userId, other.userId);
	}
	@Override
	public String toString() {
		return "Posts [postId=" + postId + ", userId=" + userId + ", postText=" + postText + ", postImage=" + postImage
				+ ", postVideo=" + Arrays.toString(postVideo) + ", timestampColumn=" + timestampColumn
				+ ", privacySetting=" + privacySetting + "]";
	}
	
	public PostsDTO toDTO() {
		PostsDTO postsDTO = new PostsDTO();
		postsDTO.setPostId(postId);
		postsDTO.setPostImage(postImage);
		postsDTO.setPostText(postText);
		postsDTO.setPostVideo(postVideo);
		postsDTO.setPrivacySetting(privacySetting);
		postsDTO.setTimestampColumn(timestampColumn);
		postsDTO.setUserId(userId);
		return postsDTO;
	}
	
	
}
